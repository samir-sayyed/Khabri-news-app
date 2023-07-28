package com.sam.khabri.data

import android.content.Context
import android.util.Log
import com.sam.khabri.data.db.ArticleDao
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.data.remote.KhabriDataSource
import com.sam.khabri.utils.ApiHandler
import com.sam.khabri.utils.ApiResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KhabriRepository @Inject constructor(
    private val khabriDataSource: KhabriDataSource,
    private val articleDao: ArticleDao,
    @ApplicationContext private val context: Context
) {

    fun getHeadings(category: String) = flow {
        try {
            val remoteResponse = ApiHandler(context).safeApiCall {
                khabriDataSource.getHeadings(category)
            }
            var remoteList: ArrayList<Article> = arrayListOf()
            when (remoteResponse) {
                is ApiResult.Success -> {

                    remoteList = remoteResponse.data?.articles as ArrayList<Article>
                    if (category == "general"){
                        articleDao.clear()
                        articleDao.insertArticle(remoteList)
                    }

                }

                else -> Unit
            }
            emit(remoteList)
        } catch (e: Exception) {
            Log.e("KhabriRepository", "getHeadings: ${e.message}")
        }

    }

}