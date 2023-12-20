package com.example.amikomchat.fragment

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amikomchat.Message
import com.example.amikomchat.MessageAdapter
import com.example.amikomchat.R
import com.google.android.gms.common.api.Response
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import com.google.common.net.MediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.security.auth.callback.Callback

class FragmentChat : Fragment() {
    val API_KEY = "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"
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: TextInputEditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private val client = OkHttpClient()
    private var isWelcomeTextVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        messageEditText = view.findViewById(R.id.message_edit_text)
        sendButton = view.findViewById(R.id.send_bt)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim { it <= ' ' }
            addToChat(question, Message.SENT_BY_ME)
            messageEditText.setText("")
            callAPI(question)
        }

        return view
    }

    private fun addToChat(message: String, sentBy: String) {

        if (isWelcomeTextVisible) {
            view?.findViewById<TextView>(R.id.welcome_text)?.visibility = View.GONE
            isWelcomeTextVisible = false
        }
        messageList.add(Message(message, sentBy))
        messageAdapter.notifyDataSetChanged()
        recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
    }

    private fun callAPI(question: String) {
        messageList.add(Message("Typing...", Message.SENT_BY_BOT))
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val body: RequestBody = RequestBody.create(JSON, jsonBody.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer $API_KEY")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                try {
                    val responseBody = response.body?.string() ?: ""
                    requireActivity().runOnUiThread {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(responseBody)
                                val jsonArray = jsonObject.getJSONArray("choices")
                                val result = jsonArray.getJSONObject(0).getString("text")
                                addResponse(result.trim())
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        } else {
                            addResponse("Failed to load response due to $responseBody")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun addResponse(response: String?) {
        messageList.removeAt(messageList.size - 1)
        addToChat(response!!, Message.SENT_BY_BOT)
    }

    companion object {
        val JSON: okhttp3.MediaType = "application/json; charset=utf-8".toMediaType()
        const val API_KEY = "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"
    }
}
