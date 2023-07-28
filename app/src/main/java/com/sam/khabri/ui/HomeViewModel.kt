package com.sam.khabri.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sam.khabri.base.BaseViewModel
import com.sam.khabri.data.KhabriRepository
import com.sam.khabri.data.db.ArticleDao
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.utils.NetworkStatusHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val khabriRepository: KhabriRepository,
    private val articleDao: ArticleDao
) : BaseViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private var _headings = MutableLiveData<List<Article>?>()
    val headings: LiveData<List<Article>?> = _headings


    fun getHeadings(category: String, context: Context) = viewModelScope.launch(Dispatchers.IO) {
        if (NetworkStatusHelper.isOnline(context))
            khabriRepository.getHeadings(category).collectLatest {
                _headings.postValue(it)
            }
        else {
            try {
                _headings.postValue(articleDao.getArticles())
            }catch (e: Exception){
                Log.e(TAG, "getHeadings: ${e.message}")
            }
        }
    }


}