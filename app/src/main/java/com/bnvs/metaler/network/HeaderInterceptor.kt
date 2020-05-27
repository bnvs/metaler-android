package com.bnvs.metaler.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private var access_token = ""
    fun setAccessToken(token: String) {
        access_token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return if (original.url.encodedPath.equals("/users/check", true)
            || original.url.encodedPath.equals("/users/terms", true)
            || original.url.encodedPath.equals("/users/join", true)
            || original.url.encodedPath.equals("/users/login", true)
        ) {
            chain.proceed(original)
        } else if (original.url.encodedPath.equals("/uploadFile.php", true)) {
            chain.proceed(
                original.newBuilder().url("http://file.metaler.kr/uploadFile.php").build()
            )
        } else {
            chain.proceed(original.newBuilder().apply {
                addHeader("Authorization", access_token)
            }.build())
        }
    }
}