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
import com.example.newsapp.utils.Constants.DEFAULT_SWIPER_DELAY
import com.example.newsapp.utils.Constants.NEWS_CONTENT
import com.example.newsapp.utils.Constants.NEWS_DESCRIPTION
import com.example.newsapp.utils.Constants.NEWS_IMAGE_URL
import com.example.newsapp.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.newsapp.utils.Constants.NEWS_SOURCE
import com.example.newsapp.utils.Constants.NEWS_TITLE
import com.example.newsapp.utils.Constants.NEWS_URL
import com.example.newsapp.utils.Constants.TOP_HEADLINES_COUNT
import com.example.newsapp.utils.Constants.INITIAL_POSITION

class GeneralFragment : Fragment() {

    private lateinit var mainHandler: Handler
    private lateinit var swiper: Runnable
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewTop: RecyclerView
    private lateinit var topAdapter: CustomAdapterForTopHeadlines
    private lateinit var adapter: CustomAdapter
    private lateinit var newsDataForTopHeadlines: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    var position = INITIAL_POSITION

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
        newsDataForTopHeadlines = MainActivity.generalNews.slice(0 until TOP_HEADLINES_COUNT)
        newsDataForDown = MainActivity.generalNews.slice(TOP_HEADLINES_COUNT until MainActivity.generalNews.size - TOP_HEADLINES_COUNT)
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
                mainHandler.postDelayed(this, DEFAULT_SWIPER_DELAY)
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
                val intent = Intent(context, ReadNewsActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForDown[position].url)
                    putExtra(NEWS_TITLE, newsDataForDown[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForDown[position].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForDown[position].description)
                    putExtra(NEWS_SOURCE, newsDataForDown[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                    putExtra(NEWS_CONTENT, newsDataForDown[position].content)
                }

                startActivity(intent)
            }
        })

        //TOPHEADLINES LIST ITEM ONCLICK
        topAdapter.setOnItemClickListener(object :
            CustomAdapterForTopHeadlines.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val pos = position % TOP_HEADLINES_COUNT

                val intent = Intent(context, ReadNewsActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForTopHeadlines[pos].url)
                    putExtra(NEWS_TITLE, newsDataForTopHeadlines[pos].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForTopHeadlines[pos].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForTopHeadlines[pos].description)
                    putExtra(NEWS_SOURCE, newsDataForTopHeadlines[pos].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForTopHeadlines[pos].time)
                    putExtra(NEWS_CONTENT, newsDataForTopHeadlines[pos].content)
                }

                startActivity(intent)
            }
        })

        // Ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }

    private var currentSliderPosition = INITIAL_POSITION

    // pause slider when fragment paused
    override fun onPause() {
        mainHandler.removeCallbacks(swiper)
        currentSliderPosition = position
        super.onPause()
    }

    // resume slider when fragment resumed
    override fun onResume() {
        mainHandler.post(swiper)
        position = currentSliderPosition
        super.onResume()
    }

}