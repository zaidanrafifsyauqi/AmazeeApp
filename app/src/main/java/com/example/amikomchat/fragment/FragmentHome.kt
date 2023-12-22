package com.example.amikomchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

import com.example.amikomchat.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment(R.layout.fragment_home) {
    private lateinit var imageSlider: Any

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val BtnChat : ImageView = view.findViewById(R.id.menu1)
        val BtnFeed : ImageView = view.findViewById(R.id.menu2)
        val BtnTodo : ImageView = view.findViewById(R.id.menu3)
        val BtnOrganisasi : ImageView = view.findViewById(R.id.menu4)

        BtnChat.setOnClickListener {
            val chat = FragmentChat()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,chat)?.commit()
        }

        BtnFeed.setOnClickListener {
            val feed = FragmentNews()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,feed)?.commit()
        }

//        BtnTodo.setOnClickListener {
//            val todo = FragmentOrganisasi()
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.fragment_container,organisasi)?.commit()
//        }
        
        BtnOrganisasi.setOnClickListener {
            val organisasi = FragmentOrganisasi()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,organisasi)?.commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSWEcld-8qPibqqoT5oEWHznz5fBHgYxw9rQ&usqp=CAU"))
        imageList.add(SlideModel("https://home.amikom.ac.id/media/2020/01/HEAD.jpg"))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}