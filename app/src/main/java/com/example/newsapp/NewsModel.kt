package com.example.newsapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News_Table")
data class NewsModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "headline")
    val headLine: String,

    @ColumnInfo(name = "imgurl")
    val image: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "source")
    val source: String?,

    @ColumnInfo(name = "time")
    val time: String?,

    @ColumnInfo(name = "content")
    val content: String?

)