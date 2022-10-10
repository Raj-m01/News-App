package com.example.newsapp.fragmentClasses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.NewsModel
import com.example.newsapp.R
import com.example.newsapp.ReadNewsActivity
import com.example.newsapp.adapters.CustomAdapter
import com.example.newsapp.utils.Constants.NEWS_CONTENT
import com.example.newsapp.utils.Constants.NEWS_DESCRIPTION
import com.example.newsapp.utils.Constants.NEWS_IMAGE_URL
import com.example.newsapp.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.newsapp.utils.Constants.NEWS_SOURCE
import com.example.newsapp.utils.Constants.NEWS_TITLE
import com.example.newsapp.utils.Constants.NEWS_URL
import com.example.newsapp.utils.Constants.TOP_HEADLINES_COUNT
import com.example.newsapp.utils.Constants.INITIAL_POSITION
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.squareup.picasso.Picasso

class GeneralFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var carouselView: CarouselView
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

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        // Setting recyclerViews adapter
        newsDataForTopHeadlines = MainActivity.generalNews.slice(0 until TOP_HEADLINES_COUNT)
        newsDataForDown = MainActivity.generalNews.slice(TOP_HEADLINES_COUNT until MainActivity.generalNews.size - TOP_HEADLINES_COUNT)
        adapter = CustomAdapter(newsDataForDown)
        recyclerView.adapter = adapter


        carouselView = view.findViewById<CarouselView>(R.id.home_carousel)

        carouselView.apply {
            size = newsDataForTopHeadlines.size
            autoPlay = true
            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.img)
                Picasso.get()
                    .load(newsDataForTopHeadlines[position].image)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.samplenews)
                    .into(imageView)


                val newsTitle = view.findViewById<TextView>(R.id.headline)
                newsTitle.text = newsDataForTopHeadlines[position].headLine

                view.setOnClickListener {

                    val intent = Intent(context, ReadNewsActivity::class.java).apply {
                        putExtra(NEWS_URL, newsDataForTopHeadlines[position].url)
                        putExtra(NEWS_TITLE, newsDataForTopHeadlines[position].headLine)
                        putExtra(NEWS_IMAGE_URL, newsDataForTopHeadlines[position].image)
                        putExtra(NEWS_DESCRIPTION, newsDataForTopHeadlines[position].description)
                        putExtra(NEWS_SOURCE, newsDataForTopHeadlines[position].source)
                        putExtra(NEWS_PUBLICATION_TIME, newsDataForTopHeadlines[position].time)
                        putExtra(NEWS_CONTENT, newsDataForTopHeadlines[position].content)
                    }

                    startActivity(intent)

                }
            }
            // After you finish setting up, show the CarouselView
            show()
        }

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

        // Ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }

}