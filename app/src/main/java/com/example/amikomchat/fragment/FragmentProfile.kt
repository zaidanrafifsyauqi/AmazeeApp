package com.example.amikomchat.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.amikomchat.ActivityLogin
import com.example.amikomchat.ActivitySplashLogin
import com.example.amikomchat.R

class FragmentProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Tambahkan logika logout di sini
            logout()
        }

        return view
    }

    private fun logout() {
        // Hapus status login dari SharedPreferences
        saveLoginStatus(false)

        // Pindah ke halaman login
        val intent = Intent(activity, ActivitySplashLogin::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun saveLoginStatus(status: Boolean) {
        val sharedPreferences = context?.getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("isLoggedIn", status)
        editor?.apply()
    }
}
