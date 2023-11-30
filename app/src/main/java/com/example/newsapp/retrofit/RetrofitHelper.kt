package com.example.newsapp.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.newsapp.NewsApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT = 60
object RetrofitHelper {

    private const val BASE_URL = "https://newsapi.org/v2/"

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(NewsApplication.applicationContext()))
        .addInterceptor(loggingInterceptor)
        .build()

    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
           .client(client)
            .build()
    }

}