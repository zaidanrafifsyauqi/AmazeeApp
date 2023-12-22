package com.example.amikomchat

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.amikomchat.model.ModelTdl
class TdlAdapter(private var data: List<ModelTdl>, private val db: TdlDBhelper) : RecyclerView.Adapter<TdlAdapter.TdlViewHolder>() {

    class TdlViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.judul)
        var prioritas: TextView = itemView.findViewById(R.id.prioritas)
        var editTdl: ImageButton = itemView.findViewById(R.id.imageViewEditTdl)
        var deleteTdl: ImageButton = itemView.findViewById(R.id.imageViewDeleteTdl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TdlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tdl_item, parent, false)
        return TdlViewHolder(view)
    }

    override fun onBindViewHolder(holder: TdlViewHolder, position: Int) {
        val tdl = data[position]

        holder.judul.text = tdl.judul
        holder.prioritas.text = tdl.prioritas

        holder.editTdl.setOnClickListener {
            val intent = Intent(holder.itemView.context, TdlEditActivity::class.java).apply{
                putExtra("tdlId", tdl.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteTdl.setOnClickListener {
            db.deleteTdl(tdl.id)
            updateData(db.showTDL())
            Toast.makeText(holder.itemView.context, "List Telah Dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<ModelTdl>) {
        data = newData
        notifyDataSetChanged()
    }



}


