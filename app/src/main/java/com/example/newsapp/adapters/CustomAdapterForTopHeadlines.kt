package com.example.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.NewsModel
import com.example.newsapp.R
import com.squareup.picasso.Picasso


class CustomAdapterForTopHeadlines(private var newsList: List<NewsModel>) :
    RecyclerView.Adapter<CustomAdapterForTopHeadlines.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    private lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(ItemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(ItemView) {

        val image: ImageView = itemView.findViewById(R.id.img)
        val headLine: TextView = itemView.findViewById(R.id.headline)

        init {
            ItemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_for_top_headlines, parent, false)
        context = parent.context
        return ViewHolder(view, mListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = position % MainActivity.TOP_HEADLINES_COUNT
        val newsData: NewsModel = newsList[pos]

        holder.headLine.text = newsData.headLine

        val imgUrl = newsData.image
        if (imgUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(R.drawable.samplenews)
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
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}