package com.example.newsapp

import android.app.Application
import android.content.Context

class NewsApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: NewsApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}