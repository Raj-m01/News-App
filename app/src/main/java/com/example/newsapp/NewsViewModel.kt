package com.example.newsapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.*


class NewsViewModel() : ViewModel() {

     var NewsLiveData: MutableLiveData<List<NewsModel>>? = null


    //get news from API
    fun getNews() : MutableLiveData<List<NewsModel>>?{

        NewsLiveData = NewsRepository().getNewsApiCall()

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