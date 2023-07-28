package com.sam.khabri.data.remote

import com.sam.khabri.nertwork.KhabriService
import javax.inject.Inject

class KhabriDataSource @Inject constructor(
    private val khabriService: KhabriService
) {
    suspend fun getHeadings(category: String) =
        khabriService.getHeadlines(category = category)
}