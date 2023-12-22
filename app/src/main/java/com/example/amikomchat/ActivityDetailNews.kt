package com.example.amikomchat

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class ActivityDetailNews : AppCompatActivity() {

    var title: String = ""
    var ingredient: String = ""
    var step: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val imgThumb: ImageView = findViewById(R.id.img_thumb)
        val tvTitle: TextView = findViewById(R.id.tv_title)
//        val tvTimes: TextView = findViewById(R.id.tv_times)
//        val tvServing: TextView = findViewById(R.id.tv_serving)
//        val tvDifficulty: TextView = findViewById(R.id.tv_difficulty)
        val tvBahanBahan: TextView = findViewById(R.id.tv_bahan_bahan)
//        val tvBaraMembuat: TextView = findViewById(R.id.tv_cara_membuat)

        intent.getStringArrayListExtra("ingredient")?.forEachIndexed{
                x, y -> ingredient += "- $y\n"
        }

        intent.getStringArrayListExtra("ingredient")?.forEachIndexed{
                x, y -> step += "${x+1}. $y\n"
        }
        title = intent.getStringExtra("title") ?: ""

        if(intent.hasExtra("thumb")) imgThumb.setImageResource(intent.getIntExtra("thumb",0))
        tvTitle.text = title
//        tvTimes.text = intent.getStringExtra("times")
//        tvServing.text = intent.getStringExtra("serving")
//        tvDifficulty.text = intent.getStringExtra("difficulty")
        tvBahanBahan.text = ingredient
//        tvBaraMembuat.text = step

        supportActionBar?.title = getString(R.string.detail_resep)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.action_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                this.finish()
                return true
            }
//            R.id.action_share -> {
//                val intent = Intent(Intent.ACTION_SEND)
//                intent.type = "text/plain"
//                intent.putExtra(Intent.EXTRA_TEXT, "$title\n\nBahan Bahan\n$ingredient\nCara Membuat\n$step")
//                startActivity(Intent.createChooser(intent, getString(R.string.menu_share)))
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}