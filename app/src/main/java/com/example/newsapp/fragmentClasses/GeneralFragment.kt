package com.example.newsapp.fragmentClasses

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.newsapp.adapters.CustomAdapterForTopHeadlines

class GeneralFragment : Fragment() {

    private lateinit var mainHandler: Handler
    private lateinit var swiper: Runnable
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewTop: RecyclerView
    private lateinit var topAdapter: CustomAdapterForTopHeadlines
    private lateinit var adapter: CustomAdapter
    private lateinit var newsDataForTopHeadlines: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_general, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val layoutManagerTop = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerViewTop = view.findViewById(R.id.recyclerView_top)
        recyclerView.layoutManager = layoutManager
        recyclerViewTop.layoutManager = layoutManagerTop

        // Setting recyclerViews adapter
        newsDataForTopHeadlines =
            MainActivity.generalNews.slice(0 until MainActivity.TOP_HEADLINES_COUNT)
        newsDataForDown =
            MainActivity.generalNews.slice(MainActivity.TOP_HEADLINES_COUNT until MainActivity.generalNews.size - MainActivity.TOP_HEADLINES_COUNT)
        topAdapter = CustomAdapterForTopHeadlines(newsDataForTopHeadlines)
        adapter = CustomAdapter(newsDataForDown)
        recyclerViewTop.adapter = topAdapter
        recyclerView.adapter = adapter

        // Top headlines items Slider
        mainHandler = Handler(Looper.getMainLooper())
        swiper = object : Runnable {
            override fun run() {
                recyclerViewTop.smoothScrollToPosition(position)
                position++
                mainHandler.postDelayed(this, 4000)
            }
        }

        var stateChanged = false

        recyclerViewTop.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mainHandler.removeCallbacks(swiper)
                    stateChanged = true
                }

                if (stateChanged && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    position = layoutManagerTop.findFirstVisibleItemPosition() + 1
                    mainHandler.post(swiper)
                    stateChanged = false
                }
            }
        })

        // listitem onClick
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ReadNewsActivity::class.java)
                intent.putExtra(MainActivity.NEWS_URL, newsDataForDown[position].url)
                intent.putExtra(MainActivity.NEWS_TITLE, newsDataForDown[position].headLine)
                intent.putExtra(MainActivity.NEWS_IMAGE_URL, newsDataForDown[position].image)
                intent.putExtra(
                    MainActivity.NEWS_DESCRIPTION,
                    newsDataForDown[position].description
                )
                intent.putExtra(MainActivity.NEWS_SOURCE, newsDataForDown[position].source)
                intent.putExtra(MainActivity.NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                intent.putExtra(MainActivity.NEWS_CONTENT, newsDataForDown[position].content)
                startActivity(intent)
            }
        })

        //TOPHEADLINES LIST ITEM ONCLICK
        topAdapter.setOnItemClickListener(object :
            CustomAdapterForTopHeadlines.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(context, ReadNewsActivity::class.java)
                val pos = position % MainActivity.TOP_HEADLINES_COUNT

                intent.putExtra(MainActivity.NEWS_URL, newsDataForTopHeadlines[pos].url)
                intent.putExtra(MainActivity.NEWS_TITLE, newsDataForTopHeadlines[pos].headLine)
                intent.putExtra(MainActivity.NEWS_IMAGE_URL, newsDataForTopHeadlines[pos].image)
                intent.putExtra(
                    MainActivity.NEWS_DESCRIPTION,
                    newsDataForTopHeadlines[pos].description
                )
                intent.putExtra(MainActivity.NEWS_SOURCE, newsDataForTopHeadlines[pos].source)
                intent.putExtra(
                    MainActivity.NEWS_PUBLICATION_TIME,
                    newsDataForTopHeadlines[pos].time
                )
                intent.putExtra(MainActivity.NEWS_CONTENT, newsDataForTopHeadlines[pos].content)
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

    private var currentPositonOfSlider = 0

    // pause slider when fragment paused
    override fun onPause() {
        mainHandler.removeCallbacks(swiper)
        currentPositonOfSlider = position
        super.onPause()
    }

    // resume slider when fragment resumed
    override fun onResume() {
        mainHandler.post(swiper)
        position = currentPositonOfSlider
        super.onResume()
    }

}