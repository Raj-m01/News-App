package com.example.newsapp

import NewsDataFromJson
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=5a3e054de1834138a2fbc4a75ee69053

    //https://newsapi.org/v2/top-headlines?country=in&apiKey=5a3e054de1834138a2fbc4a75ee69053

    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country : String, @Query("apiKey") key : String) : Call<NewsDataFromJson>


}