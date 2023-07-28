package com.sam.khabri.di

import android.content.Context
import androidx.room.Room
import com.sam.khabri.data.db.AppDatabase
import com.sam.khabri.data.db.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "khabri_database").build()


    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.getArticleDao()

}