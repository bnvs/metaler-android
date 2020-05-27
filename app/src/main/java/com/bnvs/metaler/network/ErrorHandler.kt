package com.bnvs.metaler.network

import retrofit2.HttpException

object ErrorHandler {

    fun getErrorCode(e: HttpException): Int {
        return if (e.code() == 401) {
            when (e.message()) {
                "No Authorization headers" -> NO_HEADER
                "signin_token_expired" -> TOKEN_EXPIRED
                "access_token_expired" -> TOKEN_EXPIRED
                else -> NO_ERROR_TO_HANDLE
            }

        } else {
            NO_ERROR_TO_HANDLE
        }
    }

}