package com.sam.khabri.nertwork

import com.sam.khabri.data.model.response.HeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KhabriService {

    @GET("top-headlines")
    suspend fun getHeadlines(@Query("country") country: String= "in",
                             @Query("category") category: String) : Response<HeadlinesResponse>

}