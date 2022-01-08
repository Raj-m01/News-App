package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment() {


    lateinit var viewModel: NewsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var fetchedNews: MutableList<NewsModel>
    lateinit var mAdapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View =  inflater.inflate(R.layout.fragment_main, container, false)


        progressBar = view.findViewById(R.id.progressbar)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        fetchedNews = mutableListOf<NewsModel>()


        progressBar.visibility = View.VISIBLE


        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(View.OnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToSavedNewsFragment4()

            it.findNavController().navigate(action)


        })



        mAdapter = CustomAdapter(fetchedNews, object : CustomAdapter.SaveClickListener{
            override fun onSaveBtnClick(position: Int) {

                context?.let { viewModel.insertNews(it,fetchedNews.get(position)) }

                Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

            }
        })


        viewModel.getNews()?.observe(viewLifecycleOwner, Observer<List<NewsModel>>(){

            fun onChanged(@Nullable news: List<NewsModel>) {
                // do something
                fetchedNews.addAll(news)
                recyclerView.adapter = mAdapter
                progressBar.visibility = View.GONE
                mAdapter.notifyDataSetChanged()
            }

            onChanged(it)

        })

    return view
    }

}