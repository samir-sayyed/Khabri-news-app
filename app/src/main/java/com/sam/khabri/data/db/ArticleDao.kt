package com.sam.khabri.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.khabri.data.model.response.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun getArticles(): List<Article>

    @Query("DELETE FROM article")
    fun clear()
}