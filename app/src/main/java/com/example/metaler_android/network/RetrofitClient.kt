package com.example.metaler_android.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client: RetrofitInterface
    private const val BASE_URL = ""

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        client = retrofit.create(RetrofitInterface::class.java)
    }
}