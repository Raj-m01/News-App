package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedNewsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: NewsViewModel
    lateinit var newData: MutableList<NewsModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_saved_news, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        newData = mutableListOf()


        val adapter = CustomAdapter(newData, object : CustomAdapter.SaveClickListener{
            override fun onSaveBtnClick(position: Int) {

            }
        })

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        context?.let {
            viewModel.getNewsFromDB(it.applicationContext)?.observe(viewLifecycleOwner, Observer {
                    newData.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }



        recyclerView.adapter = adapter

    return view
    }

}