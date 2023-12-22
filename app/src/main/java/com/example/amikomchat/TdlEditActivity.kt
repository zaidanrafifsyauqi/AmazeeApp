package com.example.amikomchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.amikomchat.model.ModelTdl

class TdlEditActivity : AppCompatActivity() {

    private lateinit var db: TdlDBhelper
    private var tdlID: Int = -1

    companion object {
        var newJudul = "judul"
        var newPrioritas = "prioritas"
        var newDeskripsi = "deskripsi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tdl_edit)

        db = TdlDBhelper(this)

        tdlID = intent.getIntExtra("tdlId", -1)
        if (tdlID == -1) {
            finish()
            return
        }

        val judul: EditText = findViewById(R.id.EditJudul)
        val prioritas: EditText = findViewById(R.id.EditPrioritas)
        val deskripsi: EditText = findViewById(R.id.EditDeskripsi)

        val btnSaveEdit: ImageView = findViewById(R.id.ImageViewSaveEditTDL)

        val tdl = db.getTdlById(tdlID)
        judul.setText(tdl?.judul ?: "")
        prioritas.setText(tdl?.prioritas ?: "")
        deskripsi.setText(tdl?.deskkripsi ?: "")

        btnSaveEdit.setOnClickListener {
            val newTdl = ModelTdl(tdlID, judul.text.toString(), prioritas.text.toString(), deskripsi.text.toString())
            db.editTdl(newTdl)

            val intent = Intent(this, TdlActivity::class.java)
            startActivity(intent)

            finish()
        }

        val btnCancel: Button = findViewById(R.id.buttonCancel)
        btnCancel.setOnClickListener {
            val intent = Intent(this, TdlActivity::class.java)
            startActivity(intent)
        }

    }
}
