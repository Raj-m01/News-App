package com.example.newsapp.architecture

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.BuildConfig
import com.example.newsapp.MainActivity
import com.example.newsapp.NewsModel
import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.retrofit.NewsDataFromJson
import com.example.newsapp.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    companion object {

        private var newsDatabase: NewsDatabase? = null

        private fun initializeDB(context: Context): NewsDatabase {
            return NewsDatabase.getDatabaseClient(context)
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

        fun getAllNews(context: Context): LiveData<List<NewsModel>> {

            newsDatabase = initializeDB(context)
            return newsDatabase!!.newsDao().getNewsFromDatabase()
        }

    }

    // get news from API
    fun getNewsApiCall(category: String?): MutableLiveData<List<NewsModel>> {

        val newsList = MutableLiveData<List<NewsModel>>()

        val call = RetrofitHelper.getInstance().create(NewsApi::class.java)
            .getNews("in", category, BuildConfig.API_KEY) //put your api key here

        call.enqueue(object : Callback<NewsDataFromJson> {
            override fun onResponse(
                call: Call<NewsDataFromJson>,
                response: Response<NewsDataFromJson>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {
                        val tempNewsList = mutableListOf<NewsModel>()

                        body.articles.forEach {
                            tempNewsList.add(
                                NewsModel(
                                    it.title,
                                    it.urlToImage,
                                    it.description,
                                    it.url,
                                    it.source.name,
                                    it.publishedAt,
                                    it.content
                                )
                            )
                        }
                        newsList.value = tempNewsList
                    }

                } else {

                    val jsonObj: JSONObject?

                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            MainActivity.apiRequestError = true
                            MainActivity.errorMessage = jsonObj.getString("message")
                            val tempNewsList = mutableListOf<NewsModel>()
                            newsList.value = tempNewsList
                        }
                    } catch (e: JSONException) {
                        Log.d("JSONException", "" + e.message)
                    }

                }
            }

            override fun onFailure(call: Call<NewsDataFromJson>, t: Throwable) {

                MainActivity.apiRequestError = true
                MainActivity.errorMessage = t.localizedMessage as String
                Log.d("err_msg", "msg" + t.localizedMessage)
            }
        })

        return newsList
    }

}

