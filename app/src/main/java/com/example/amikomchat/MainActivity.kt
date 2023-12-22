package com.example.amikomchat

import FragmentNews
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.amikomchat.fragment.FragmentChat
import com.example.amikomchat.fragment.FragmentHome
import com.example.amikomchat.fragment.FragmentProfile
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val fragmentHome = FragmentHome()
    private val fragmentChat = FragmentChat()
    private val fragmentNews = FragmentNews()
    private val fragmentProfile = FragmentProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_1 -> replaceFragment(fragmentHome)
                R.id.menu_item_2 -> replaceFragment(fragmentChat)
                R.id.menu_item_3 -> replaceFragment(fragmentNews)
                R.id.menu_item_4 -> replaceFragment(fragmentProfile)
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.menu_item_1

        // Menampilkan Fragment pertama saat aplikasi pertama kali dijalankan
        if (savedInstanceState == null) {
            replaceFragment(fragmentHome)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
