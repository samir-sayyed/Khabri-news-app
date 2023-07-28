package com.sam.khabri.nertwork

import com.sam.khabri.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder()
        currentRequest.addHeader("Authorization", "Bearer $API_KEY")
        return chain.proceed(currentRequest.build())
    }
}