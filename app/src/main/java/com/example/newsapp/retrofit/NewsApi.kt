package com.example.newsapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=your_api_key


    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country : String, @Query("category") category : String?, @Query("apiKey") key : String) : Call<NewsDataFromJson>


    // for global
//    https://newsapi.org/v2/top-headlines?category=general&language=en&apiKey=5a3e054de1834138a2fbc4a75ee69053

}