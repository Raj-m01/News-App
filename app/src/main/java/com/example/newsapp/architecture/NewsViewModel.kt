package com.example.newsapp.architecture

import android.content.Context
import androidx.lifecycle.*
import com.example.newsapp.NewsModel


class NewsViewModel() : ViewModel() {

     var NewsLiveData: MutableLiveData<List<NewsModel>>? = null


    //get news from API
    fun getNews(category: String?) : MutableLiveData<List<NewsModel>>?{

        NewsLiveData = category?.let { NewsRepository().getNewsApiCall(it) }

        return NewsLiveData
    }



    var newsData: LiveData<List<NewsModel>>? = null

    fun insertNews(context: Context, news: NewsModel) {
        NewsRepository.insertNews(context, news)
    }

    fun deleteNews(context: Context, news: NewsModel) {
        NewsRepository.deleteNews(context, news)
    }

    fun getNewsFromDB(context: Context) : LiveData<List<NewsModel>>? {
        newsData = NewsRepository.getAllNews(context)
        return newsData
    }


}