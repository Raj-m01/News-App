package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.architecture.NewsViewModel

class SavedNews : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: NewsViewModel
    lateinit var newData: MutableList<NewsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        newData = mutableListOf()

        val adapter = CustomAdapter(newData, object : CustomAdapter.SaveClickListener{
            override fun onSaveBtnClick(position: Int) {
                this@SavedNews?.let { viewModel.deleteNews(it,newData.get(position)) }
            }
        })



        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        this?.let {
            viewModel.getNewsFromDB(it.applicationContext)?.observe(this, Observer {
                newData.clear()
                newData.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }

        recyclerView.adapter = adapter

    }


}