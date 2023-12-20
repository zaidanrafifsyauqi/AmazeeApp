package com.example.amikomchat

import DBhelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBhelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DBhelper(this)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            registerUser()
        }

        val textLog = findViewById<TextView>(R.id.tv_login)

        textLog.setOnClickListener {
            val intent = Intent(this, ActivitySplashLogin::class.java)
            startActivity(intent)
        }
    }
    private fun registerUser() {
        val emailInput = findViewById<EditText>(R.id.email_reg).text.toString()
        val passwordInput = findViewById<EditText>(R.id.pass_reg).text.toString()
        val usernameInput = findViewById<EditText>(R.id.username_reg).text.toString()

        val userId = dbHelper.addUser(usernameInput, emailInput, passwordInput)

        if (userId > 0) {
                // Registrasi berhasil, pindah ke MainActivity
                val intent = Intent(this, ActivitySplashLogin::class.java)
                startActivity(intent)
                finish()
            } else {
                // Registrasi gagal, tampilkan pesan kesalahan
                // (Anda bisa menambahkan TextView untuk menampilkan pesan kesalahan)
            }
    }
}