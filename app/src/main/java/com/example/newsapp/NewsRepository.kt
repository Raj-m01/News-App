package com.example.newsapp

import NewsDataFromJson
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {


    val newsList = MutableLiveData<List<NewsModel>>()

    companion object {

        var newsDatabase: NewsDatabase? = null
        var newsList: LiveData<List<NewsModel>>? = null

        fun initializeDB(context: Context) : NewsDatabase {
            return NewsDatabase.getDataseClient(context)
        }

        fun insertNews(context: Context, news: NewsModel) {

            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.newsDao().insertNews(news)
            }
        }

        fun deleteNews(context: Context, news: NewsModel) {

            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.newsDao().deleteNews(news)
            }
        }

        fun getAllNews(context: Context) : LiveData<List<NewsModel>>? {

            newsDatabase = initializeDB(context)
            newsList = newsDatabase!!.newsDao().getNewsFromDatabase()
            return newsList
        }

    }

    // get news from API
    fun getNewsApiCall(): MutableLiveData<List<NewsModel>> {

        val call = RetrofitHelper.getInstance().create(NewsApi::class.java)
            .getNews("in", "5a3e054de1834138a2fbc4a75ee69053")

        call.enqueue(object : Callback<NewsDataFromJson> {
            override fun onResponse(
                call: Call<NewsDataFromJson>,
                response: Response<NewsDataFromJson>
            ) {

                val body = response.body()
                if (body != null) {
                    val tempNewsList = mutableListOf<NewsModel>()
                    body.articles.forEach {

                        tempNewsList.add(NewsModel(it.title, it.urlToImage, it.description,it.url))

                    }

                    newsList.value = tempNewsList

                }
            }
            override fun onFailure(call: Call<NewsDataFromJson>, t: Throwable) {
            }
        })

    return newsList
    }


}

