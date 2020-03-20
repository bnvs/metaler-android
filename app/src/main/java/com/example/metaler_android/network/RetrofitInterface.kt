package com.example.metaler_android.network

import com.example.metaler_android.data.comment.Comments
import com.example.metaler_android.data.homepost.HomePosts
import com.example.metaler_android.data.job.Job
import com.example.metaler_android.data.category.Categories
import com.example.metaler_android.data.comment.CommentRequest
import com.example.metaler_android.data.post.Posts
import com.example.metaler_android.data.post.PostsRequest
import com.example.metaler_android.data.postdetail.PostDetails
import com.example.metaler_android.data.user.User
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
    fun getHomePosts(@Body request: JSONObject): Call<HomePosts>

    @GET("/categories")
    fun getCategories(@Body request: JSONObject): Call<Categories>

    @GET("/posts")
    fun getPosts(
        @Query("category_type") category_type: String,
        @Body request: PostsRequest): Call<Posts>

    @GET("/posts/{id}")
    fun getPostDetails(@Body request: JSONObject): Call<PostDetails>

    @GET("/posts/{id}/comments")
    fun getComments(@Body request: JSONObject): Call<Comments>

    @GET("/users/{uid}/bookmarks/{pid}")
    fun getBookmarks(): Call<Posts>

    @GET("/users/job")
    fun getJob(): Call<Job>

    @GET("/users/{uid}/categories/{cid}/posts")
    fun getMyPosts(): Call<Posts>

    @POST("/posts/{id}/comments")
    fun addComment(@Body commentRequest: CommentRequest)

    @POST("/categories/{id}/posts")
    fun addUser(@Body user: User): Call<JSONObject>

    @POST("/categories/{id}/posts")
    fun addPost()

    @POST("/uploadFile")
    fun uploadFile(): Call<JSONObject>

    @POST("/users/{uid}/bookmarks")
    fun addBookmark(): Call<String>

    @PUT("/comments/{id}")
    fun modifyComment(@Body commentRequest: CommentRequest)

    @PUT("/categories/{cid}/posts/{pid}")
    fun modifyPost()

    @PUT("/users/{id}/nickname")
    fun modifyNickname()

    @PUT("/users/job")
    fun modifyJob()

    @DELETE("/comments/{id}")
    fun deleteComment(@Body request: JSONObject)

    @DELETE("/categories/{cid}/posts/{pid}")
    fun deletePost()

    @DELETE("/users/{uid}/bookmarks/{pid}")
    fun deleteBookmark()

    @DELETE("/users/{id}")
    fun deleteUser()
}