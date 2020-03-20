package com.example.metaler_android.network

import com.example.metaler_android.data.bookmark.Bookmarks
import com.example.metaler_android.data.bookmark.BookmarksRequest
import com.example.metaler_android.data.comment.Comments
import com.example.metaler_android.data.homepost.HomePosts
import com.example.metaler_android.data.job.Job
import com.example.metaler_android.data.category.Categories
import com.example.metaler_android.data.comment.CommentRequest
import com.example.metaler_android.data.post.PostRequest
import com.example.metaler_android.data.post.Posts
import com.example.metaler_android.data.post.PostsRequest
import com.example.metaler_android.data.post.UserPostsRequest
import com.example.metaler_android.data.postdetail.PostDetails
import com.example.metaler_android.data.user.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun getPostDetails(@Path("id") id: String,
                       @Body request: JSONObject): Call<PostDetails>

    @GET("/posts/{id}/comments")
    fun getComments(@Path("id") id: String,
                    @Body request: JSONObject): Call<Comments>

    @GET("/bookmarks/{id}")
    fun getBookmarks(@Path("id") id: String,
                     @Body request: BookmarksRequest): Call<Bookmarks>

    @GET("/users/job")
    fun getJob(): Call<Job>

    @GET("/users/posts")
    fun getMyPosts(@Body request: UserPostsRequest): Call<Posts>

    @POST("/posts/{id}/comments")
    fun addComment(@Path("id") id: String,
                   @Body commentRequest: CommentRequest)

    @POST("/posts")
    fun addPost(@Body request: PostRequest): Call<JSONObject>

    @Multipart
    @POST("/files")
    fun addFile(@Part("access_token") access_token: RequestBody,
                @Part("file") file: RequestBody,
                @Part imageFile : MultipartBody.Part): Call<JSONObject>

    @POST("/posts/{id}/bookmarks")
    fun addBookmark(@Path("id") id: String,
                    @Body request: JSONObject): Call<JSONObject>

    @POST("/categories/{id}/posts")
    fun addUser(@Path("id") id: String,
                @Body user: User): Call<JSONObject>

    @PUT("/comments/{id}")
    fun modifyComment(@Path("id") id: String,
                      @Body request: CommentRequest)

    @PUT("/posts/{id}")
    fun modifyPost(@Path("id") id: String,
                   @Body request: PostRequest)

    @PUT("/users/nickname")
    fun modifyNickname()

    @PUT("/users/job")
    fun modifyJob()

    @DELETE("/comments/{id}")
    fun deleteComment(@Path("id") id: String,
                      @Body request: JSONObject)

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: String,
                   @Body request: JSONObject)

    @DELETE("/bookmarks/{id}")
    fun deleteBookmark(@Path("id") id: String,
                       @Body request: JSONObject)

    @DELETE("/users/{id}")
    fun deleteUser()
}