package com.sam.khabri.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

class ApiHandler(private val context: Context) {

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> Response<T>,
    ): ApiResult<T> {
        return try {
            if (NetworkStatusHelper.isOnline(context)) {
                val response = apiCall()
                if (response.isSuccessful) {
                    ApiResult.Success(response.body())
                } else {
                    val errorMessage = getErrorMessage(response.errorBody())
                    ApiResult.Error(response.errorBody(), errorMessage)
                }
            } else
                ApiResult.Error(message = NO_INTERNET_CONNECTION)
        } catch (e: Exception) {
            ApiResult.Error()
        }
    }


    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE) -> jsonObject.getString(MESSAGE)
                jsonObject.has(ERROR) -> jsonObject.getString(ERROR)
                else -> SOMETHING_WENT_WRONG
            }
        } catch (e: Exception) {
            SOMETHING_WENT_WRONG
        }
    }

}
