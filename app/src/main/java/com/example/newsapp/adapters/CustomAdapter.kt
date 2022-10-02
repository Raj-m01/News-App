package com.example.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsModel
import com.example.newsapp.R
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.Instant
import java.time.ZoneId


class CustomAdapter(private var newsList: List<NewsModel>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var mClickListener: OnItemClickListener
    private lateinit var mLongClickListener: OnItemLongClickListener

    init {
        this.notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mLongClickListener = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        context = parent.context
        return ViewHolder(view, mClickListener, mLongClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsData = newsList[holder.adapterPosition]

        holder.headLine.text = newsData.headLine
        val time: String? = newsData.time
        val imgUrl = newsData.image

        if (imgUrl.isNullOrEmpty()) {
            Picasso.get()
                .load( R.drawable.samplenews)
                .fit()
                .centerCrop()
                .into(holder.image)
        } else {
            Picasso.get()
                .load(imgUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.samplenews)
                .into(holder.image)
        }

        if (context.toString().contains("SavedNews")) {
            val date = " " + time?.substring(0, time.indexOf('T', 0))
            holder.newsPublicationTime.text = date
        } else {
            val currentTimeInHours = Instant.now().atZone(ZoneId.of("Asia/Kolkata"))
            val newsTimeInHours = Instant.parse(time).atZone(ZoneId.of("Asia/Kolkata"))
            val hoursDifference = Duration.between(currentTimeInHours, newsTimeInHours)
            val hoursAgo = " " + hoursDifference.toHours().toString().substring(1) + " hour ago"
            holder.newsPublicationTime.text = hoursAgo
        }

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(
        ItemView: View,
        listener: OnItemClickListener,
        listener2: OnItemLongClickListener
    ) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.img)
        val headLine: TextView = itemView.findViewById(R.id.news_title)
        val newsPublicationTime: TextView = itemView.findViewById(R.id.news_publication_time)

        init {
            ItemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            ItemView.setOnLongClickListener {
                listener2.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }

}
