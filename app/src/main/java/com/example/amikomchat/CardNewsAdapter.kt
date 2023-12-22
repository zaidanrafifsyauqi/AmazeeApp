package com.example.amikomchat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CardNewsAdapter(private val listResep: ArrayList<News>): RecyclerView.Adapter<CardNewsAdapter.CardViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItemThumb: ImageView = itemView.findViewById(R.id.img_item_thumb)
        val tvTimes: TextView = itemView.findViewById(R.id.tv_item_times)
        //        val tvServing: TextView = itemView.findViewById(R.id.tv_item_serving)
//        val tvDifficulty: TextView = itemView.findViewById(R.id.tv_item_difficulty)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val resep = listResep[position]
        Glide.with(holder.itemView.context)
            .load(resep.thumb)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgItemThumb)
        holder.tvTimes.text = resep.times
//        holder.tvServing.text = resep.serving
//        holder.tvDifficulty.text = resep.difficulty
        holder.tvTitle.text = resep.title
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listResep[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listResep.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: News)
    }
}