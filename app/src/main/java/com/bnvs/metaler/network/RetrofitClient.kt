package com.bnvs.metaler.network

import com.bnvs.metaler.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val client: RetrofitInterface
    val retrofit: Retrofit
    private var access_token = ""
    private const val BASE_URL = "http://metaler.kr/"

    fun setAccessToken(token: String) {
        access_token = token
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                if (original.url.encodedPath.equals("/users/check", true)
                    || original.url.encodedPath.equals("/users/join", true)
                    || original.url.encodedPath.equals("/users/join", true)
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
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }).build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        client = retrofit.create(
            RetrofitInterface::class.java
        )
    }
}