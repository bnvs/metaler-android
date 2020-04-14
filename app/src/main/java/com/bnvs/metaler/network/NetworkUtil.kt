package com.bnvs.metaler.network

import com.bnvs.metaler.data.error.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.HttpException

object NetworkUtil {
    fun getErrorMessage(e: Throwable): String {
        return if (e is HttpException) {
            val errorBody = e.response().errorBody()
            if (errorBody != null) {
                getErrorResponse(errorBody).toString()
            } else {
                "${e.code()} ${e.message()}"
            }
        } else {
            "$e"
        }
    }

    fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return RetrofitClient.retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            ErrorResponse::class.java.annotations
        ).convert(errorBody)
    }
}