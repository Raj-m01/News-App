package com.example.newsapp.retrofit

data class Article(
    val author: String,
    val description: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)