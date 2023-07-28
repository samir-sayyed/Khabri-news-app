package com.sam.khabri.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sam.khabri.data.model.response.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(
    TypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}