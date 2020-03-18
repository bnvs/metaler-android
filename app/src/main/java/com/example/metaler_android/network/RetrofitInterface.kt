package com.example.metaler_android.network

import com.example.metaler_android.data.HomePosts
import com.example.metaler_android.data.Materials
import com.example.metaler_android.data.Posts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 해당 interface 에 기술된 명세들은
 * retrofit 을 사용하여 HTTP API 로 전환된다.
 * 반환되는 값은 Call<객체타입> 형태로 기술
 * */

interface RetrofitInterface {

    @GET("/home")
    fun getHomePosts(@Body access_token: String): Call<HomePosts>

    @GET("/materials")
    fun getMaterials(@Body access_token: String): Call<Materials>

    @GET("/materials/{id}/posts")
    fun getMaterialPosts(): Call<Posts>

    @POST("/categorys/{id}/posts")
    fun setPost()

}