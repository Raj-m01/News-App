package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.architecture.NewsViewModel
import com.facebook.shimmer.ShimmerFrameLayout



class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsContainer: FrameLayout
    private lateinit var fetchedNews: MutableList<NewsModel>
    private lateinit var mAdapter: CustomAdapter
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var categoryContainer: LinearLayout
    private lateinit var dropBtn: ImageButton

    private var hideCategory: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryContainer = findViewById(R.id.cate_container)
        dropBtn = findViewById(R.id.drop_btn)
        newsContainer = findViewById(R.id.news_container)
        recyclerView = findViewById(R.id.recyclerView)
        shimmerLayout = findViewById(R.id.shimmerLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        fetchedNews = mutableListOf<NewsModel>()

        findViewById<Button>(R.id.general_btn).setBackgroundColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.general_btn).setTextColor(resources.getColor(R.color.white))


        changeNews("general")

        dropBtn.setOnClickListener{

            if(!hideCategory) {

                hideCategory = true
                val h = categoryContainer.height
                slideUp(categoryContainer, h)
                dropBtn.animate().rotationBy(180f)

            }else{

                hideCategory = false
                val h = it.height
                slideDown(categoryContainer, h)
                dropBtn.animate().rotationBy(180f)
            }

        }

        mAdapter = CustomAdapter(fetchedNews, object : CustomAdapter.SaveClickListener{
            override fun onSaveBtnClick(position: Int) {

                this.let { viewModel.insertNews(this@MainActivity, fetchedNews[position]) }

                Toast.makeText(this@MainActivity,"Saved", Toast.LENGTH_SHORT).show()

            }
        })

    }


    private fun slideUp(view: View, height: Int) {

        val duration = 200

        val animate = TranslateAnimation(0f, 0f, 0f, -height.toFloat())
        animate.duration = duration.toLong()
        view.animate().alpha(0.0f)
        view.startAnimation(animate)
        view.visibility = View.GONE


    }

    private fun slideDown(view: View, height: Int) {

        val duration = 200

        val animate = TranslateAnimation(0f, 0f, -height.toFloat(),0f )
        animate.duration = duration.toLong()
        view.animate().alpha(1.0f)
        view.startAnimation(animate)
        view.visibility = View.VISIBLE


    }

    fun buttonOnClick(view:View)
    {

        // change button style
        findViewById<Button>(R.id.general_btn).setBackgroundColor(resources.getColor(R.color.white))
        findViewById<Button>(R.id.business_btn).setBackgroundColor(resources.getColor(R.color.white))
        findViewById<Button>(R.id.entertainment_btn).setBackgroundColor(resources.getColor(R.color.white))
        findViewById<Button>(R.id.health_btn).setBackgroundColor(resources.getColor(R.color.white))
        findViewById<Button>(R.id.science_btn).setBackgroundColor(resources.getColor(R.color.white))
        findViewById<Button>(R.id.sport_btn).setBackgroundColor(resources.getColor(R.color.white))

        findViewById<Button>(R.id.general_btn).setTextColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.business_btn).setTextColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.entertainment_btn).setTextColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.health_btn).setTextColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.science_btn).setTextColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(R.id.sport_btn).setTextColor(resources.getColor(R.color.purple_500))


        view.setBackgroundColor(resources.getColor(R.color.purple_500))
        findViewById<Button>(view.id).setTextColor(resources.getColor(R.color.white))


        when(view.id)
        {

            R.id.general_btn ->{
                changeNews("general")
            }

            R.id.business_btn ->{
                changeNews("business")
            }

            R.id.health_btn ->{
                changeNews("health")
            }

            R.id.entertainment_btn ->{
                changeNews("entertainment")
            }

            R.id.science_btn ->{
                changeNews("science")
            }

            R.id.sport_btn ->{
                changeNews("sport")
            }



        }
    }

    private fun changeNews(cate : String){

        shimmerLayout.startShimmer()

        viewModel.getNews(category = cate)?.observe(this, {

            fun onChanged(@Nullable news: List<NewsModel>) {

                fetchedNews.clear()
                fetchedNews.addAll(news)
                recyclerView.adapter = mAdapter
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE
                mAdapter.notifyDataSetChanged()
            }

            onChanged(it)

        })

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        intent = Intent(applicationContext, SavedNews::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

}
