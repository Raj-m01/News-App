package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsModel)

    @Query("SELECT * FROM News_Table")
    fun getNewsFromDatabase() : LiveData<List<NewsModel>>


    @Delete
    fun deleteNews(news: NewsModel)


}