package com.example.amikomchat

import DBhelper
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ActivitySplashLogin : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_login)

        dbHelper = DBhelper(this)

        val textReg = findViewById<TextView>(R.id.tv_register)
        val btnsLogin = findViewById<Button>(R.id.btn_slogin)

        // Cek status login
        if (isLoggedIn()) {
            startMainActivity()
        }

        btnsLogin.setOnClickListener {
            loginUser()
        }

        textReg.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun loginUser() {
        val emailInput = findViewById<EditText>(R.id.slogin).text.toString()
        val passwordInput = findViewById<EditText>(R.id.spassword).text.toString()

        val user = dbHelper.getUser(emailInput)

        if (user != null && user.password == passwordInput) {
            // Login berhasil, pindah ke MainActivity
            saveLoggedInEmail(emailInput)
            saveLoginStatus(true)
            startMainActivity()
        } else {
            // Password tidak cocok, tampilkan pesan kesalahan
            Toast.makeText(this, "tidak sesuai", Toast.LENGTH_SHORT).show()
        }
    }





    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Di ActivitySplashLogin
    private fun saveLoggedInEmail(email: String) {
        Log.d("SaveLoggedInEmail", "Saving email: $email")
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("loggedInEmail", email)
        editor.apply()
    }


    private fun getLoggedInEmail(): String? {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("loggedInEmail", null)
        Log.d("GetLoggedInEmail", "Retrieved email: $email")
        return email
    }


}