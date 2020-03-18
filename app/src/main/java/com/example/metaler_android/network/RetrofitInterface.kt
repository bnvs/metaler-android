package com.example.metaler_android.network

import com.example.metaler_android.data.HomePosts
import com.example.metaler_android.data.Materials
import com.example.metaler_android.data.PostDetails
import com.example.metaler_android.data.Posts
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

    @POST("/categorys/{id}/posts")
    fun setPost()

    @POST("/uploadFile")
    fun uploadFile(): Call<JSONObject>

    @POST("/users/{uid}/bookmarks")
    fun setBookmark(): Call<String>

    @PUT("/categorys/{cid}/posts/{pid}")
    fun modifyPost()

    @DELETE("/categorys/{cid}/posts/{pid}")
    fun deletePost()

    @DELETE("/users/{uid}/bookmarks/{pid}")
    fun deleteBookmark()
}