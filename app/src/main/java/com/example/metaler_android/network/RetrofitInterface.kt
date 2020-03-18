package com.example.metaler_android.network

import com.example.metaler_android.data.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

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

    @GET("/categorys/{cid}/posts/{pid}")
    fun getPostDetails(): Call<PostDetails>

    @GET("/users/{uid}/bookmarks/{pid}")
    fun getBookmarks(): Call<Posts>

    @GET("/users/job")
    fun getJob(): Call<Job>

    @GET("/users/{uid}/categorys/{cid}/posts")
    fun getMyPosts(): Call<Posts>

    @POST("/categorys/{id}/posts")
    fun addUser(@Body user: User): Call<JSONObject>

    @POST("/categorys/{id}/posts")
    fun createPost()

    @POST("/uploadFile")
    fun uploadFile(): Call<JSONObject>

    @POST("/users/{uid}/bookmarks")
    fun addBookmark(): Call<String>

    @PUT("/categorys/{cid}/posts/{pid}")
    fun modifyPost()

    @PUT("/users/{id}/nickname")
    fun modifyNickname()

    @PUT("/users/job")
    fun modifyJob()

    @DELETE("/categorys/{cid}/posts/{pid}")
    fun deletePost()

    @DELETE("/users/{uid}/bookmarks/{pid}")
    fun deleteBookmark()

    @DELETE("/users/{id}")
    fun deleteUser()
}