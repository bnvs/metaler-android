package com.bnvs.metaler.network

import com.bnvs.metaler.data.error.ErrorResponse
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import retrofit2.Retrofit

object NetworkUtil : KoinComponent {

    private val retrofit: Retrofit by inject()
    private val headerInterceptor: HeaderInterceptor by inject()

    fun setAccessToken(accessToken: String) {
        headerInterceptor.setAccessToken(accessToken)
    }

    fun getErrorMessage(e: Throwable): String {
        return (if (e is HttpException) {
            val errorBody = e.response()?.errorBody()
            if (errorBody != null) {
                "[Server Error] ${e.code()} - ${e.message()} ${getErrorResponse(errorBody)}"
            } else {
                "[Server Error] ${e.code()} - ${e.message()}"
            }
        } else {
            e.toString()
        })
    }

    private fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            ErrorResponse::class.java.annotations
        ).convert(errorBody) ?: ErrorResponse("")
    }
}