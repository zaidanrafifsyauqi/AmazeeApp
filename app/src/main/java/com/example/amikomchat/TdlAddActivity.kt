package com.example.amikomchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.amikomchat.model.ModelTdl

class TdlAddActivity : AppCompatActivity() {

    private lateinit var db: TdlDBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tdl_add)

        db = TdlDBhelper(this)

        val btnSave: ImageView = findViewById(R.id.ImageViewSaveTDL)
        val btnCancel: Button = findViewById(R.id.buttonCancel)

        btnSave.setOnClickListener{
            val judul = findViewById<EditText>(R.id.AddJudul).text.toString()
            val prioritas = findViewById<EditText>(R.id.AddPrioritas).text.toString()
            val deskripsi = findViewById<EditText>(R.id.AddDeskripsi).text.toString()
            val tdl = ModelTdl(0, judul, prioritas ,deskripsi)

            db.insertTDL(tdl)
            finish()
            Toast.makeText(this, "Notes Telah Disimpan", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TdlActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, TdlActivity::class.java)
            startActivity(intent)
            }



    }
}
