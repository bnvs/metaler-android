package com.bnvs.metaler.network

import com.bnvs.metaler.data.error.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.HttpException

object NetworkUtil {
    fun getErrorMessage(e: Throwable): String {
        return if (e is HttpException) {
            val errorBody = e.response().errorBody()
            if (errorBody != null) {
                "[Server Error] ${e.code()} - ${e.message()} ${getErrorResponse(errorBody)}"
            } else {
                "[Server Error] ${e.code()} - ${e.message()}"
            }
        } else {
            "$e"
        }
    }

    private fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return RetrofitClient.retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            ErrorResponse::class.java.annotations
        ).convert(errorBody)
    }
}