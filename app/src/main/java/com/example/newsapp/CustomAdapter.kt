package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CustomAdapter(private val newsList: MutableList<NewsModel>, val saveListener: SaveClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    lateinit var  context: Context
    private lateinit var navController: NavController

    companion object{
        var myClickListener: SaveClickListener? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        context = parent.context

        navController =  parent.findNavController()

        return ViewHolder(view)
    }


    private fun openNews(url: String, cont: Context){

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        cont.startActivity(intent)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val newsData = newsList[holder.adapterPosition]

        holder.headLine.text = newsData.headLine
        holder.description.text = newsData.description

        if(!newsData.image.isNullOrEmpty()) {
            Picasso.get().load(newsData.image).fit().centerCrop().into(holder.image)
        }else{
            holder.image.visibility = View.GONE
        }


        if(navController.currentDestination.toString().contains("savedNewsFragment4")){
            holder.saveBtn.setImageResource(R.drawable.ic_baseline_delete_outline_24)

        }


        holder.image.setOnClickListener( View.OnClickListener {
            newsData.url?.let { it1 -> openNews(it1,context) }
        })

        holder.headLine.setOnClickListener(View.OnClickListener { newsData.url?.let { it1 ->
            openNews(
                it1, context)
        } })

        holder.shareBtn.setOnClickListener(View.OnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this news : "+newsData.url)
            intent.type = "text/plain"
            context.startActivity(Intent.createChooser(intent,"Share with :"))

        })

        myClickListener = saveListener

        holder.saveBtn.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                if (myClickListener != null) {
                    myClickListener?.onSaveBtnClick(holder.adapterPosition)
                }
            }
            })

    }



    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(ItemView: View?) : RecyclerView.ViewHolder(ItemView!!){

        val saveBtn: ImageView = itemView.findViewById(R.id.save_news)
        val shareBtn: ImageView = itemView.findViewById(R.id.share_btn)
        val image: ImageView = itemView.findViewById(R.id.news_image)
        val headLine: TextView = itemView.findViewById(R.id.headline)
        val description: TextView = itemView.findViewById(R.id.description)

    }

    open interface SaveClickListener {
        fun onSaveBtnClick(position: Int)
    }

}