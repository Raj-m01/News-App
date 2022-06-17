package com.example.newsapp.fragmentClasses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.NewsModel
import com.example.newsapp.R
import com.example.newsapp.ReadNewsActivity
import com.example.newsapp.adapters.CustomAdapter

class ScienceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_science, container, false)
        val newsData: MutableList<NewsModel> = MainActivity.scienceNews
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CustomAdapter(newsData)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {

                val intent = Intent(context, ReadNewsActivity::class.java)
                intent.putExtra(MainActivity.NEWS_URL, newsData[position].url)
                intent.putExtra(MainActivity.NEWS_TITLE, newsData[position].headLine)
                intent.putExtra(MainActivity.NEWS_IMAGE_URL, newsData[position].image)
                intent.putExtra(MainActivity.NEWS_DESCRIPTION, newsData[position].description)
                intent.putExtra(MainActivity.NEWS_SOURCE, newsData[position].source)
                intent.putExtra(MainActivity.NEWS_PUBLICATION_TIME, newsData[position].time)
                intent.putExtra(MainActivity.NEWS_CONTENT, newsData[position].content)
                startActivity(intent)

            }


        })

        //ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
            }
        })

        return view
    }


}