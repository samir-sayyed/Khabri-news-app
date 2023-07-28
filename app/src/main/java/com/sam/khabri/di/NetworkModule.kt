package com.sam.khabri.di

import android.content.Context
import com.sam.khabri.data.KhabriRepository
import com.sam.khabri.data.db.ArticleDao
import com.sam.khabri.data.remote.KhabriDataSource
import com.sam.khabri.nertwork.KhabriService
import com.sam.khabri.nertwork.NetworkInterceptor
import com.sam.khabri.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesKhabriService(retrofit: Retrofit): KhabriService {
        return retrofit.create(KhabriService::class.java)
    }

    @Singleton
    @Provides
    fun provideKhabriRepository(@ApplicationContext context: Context, khabriDataSource: KhabriDataSource, articleDao: ArticleDao): KhabriRepository{
        return KhabriRepository(khabriDataSource, articleDao, context)
    }

}