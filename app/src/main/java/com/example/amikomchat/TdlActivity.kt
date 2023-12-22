package com.example.amikomchat

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TdlActivity : AppCompatActivity() {

    private lateinit var db: TdlDBhelper
    private lateinit var tdlAdapter: TdlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tdl)

        db = TdlDBhelper(this)
        tdlAdapter = TdlAdapter(db.showTDL(), db)

        val recyclerView = findViewById<RecyclerView>(R.id.TdlRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tdlAdapter

        val btnAddTdl: ImageView = findViewById(R.id.buttonAddTdl)

        btnAddTdl.setOnClickListener {
            val intent = Intent(this, TdlAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        tdlAdapter.updateData(db.showTDL())
    }
}
