package com.example.newsapp.architecture

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.NewsModel
import com.example.newsapp.utils.Constants.DATABASE_NAME

@Database(entities = [NewsModel::class], version = 3, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabaseClient(context: Context): NewsDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

    }

}