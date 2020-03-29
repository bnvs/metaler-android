package com.bnvs.metaler.network

import com.bnvs.metaler.data.bookmarks.Bookmarks
import com.bnvs.metaler.data.bookmarks.BookmarksRequest
import com.bnvs.metaler.data.comments.Comments
import com.bnvs.metaler.data.homeposts.HomePosts
import com.bnvs.metaler.data.categories.Categories
import com.bnvs.metaler.data.comments.CommentsRequest
import com.bnvs.metaler.data.job.Jobs
import com.bnvs.metaler.data.post.PostRequest
import com.bnvs.metaler.data.post.Posts
import com.bnvs.metaler.data.post.PostsRequest
import com.bnvs.metaler.data.post.UserPostsRequest
import com.bnvs.metaler.data.postdetails.PostDetails
import com.bnvs.metaler.data.user.CheckMembershipRequest
import com.bnvs.metaler.data.user.DeleteUserRequest
import com.bnvs.metaler.data.user.NicknameRequest
import com.bnvs.metaler.data.user.AddUserRequest
import com.bnvs.metaler.data.user.CheckMembershipResponse
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

    @GET("/users/jobs")
    fun getJob(@Body request: JSONObject): Call<Jobs>

    @GET("/users/posts")
    fun getMyPosts(@Body request: UserPostsRequest): Call<Posts>

    @POST("/users/check")
    fun checkUserMembership(@Body request: CheckMembershipRequest): Call<CheckMembershipResponse>

    @POST("/posts/{id}/comments")
    fun addComment(@Path("id") id: String,
                   @Body commentRequest: CommentsRequest)

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

    // TODO : user 추가 함수 (회원가입 api)
    @POST("/categories/{id}/posts")
    fun addUser(@Path("id") id: String,
                @Body user: AddUserRequest): Call<JSONObject>

    @PUT("/comments/{id}")
    fun modifyComment(@Path("id") id: String,
                      @Body request: CommentsRequest)

    @PUT("/posts/{id}")
    fun modifyPost(@Path("id") id: String,
                   @Body request: PostRequest)

    @PUT("/users/nickname")
    fun modifyNickname(@Body request: NicknameRequest)

    @PUT("/users/jobs")
    fun modifyJob(@Body request: Jobs)

    @DELETE("/comments/{id}")
    fun deleteComment(@Path("id") id: String,
                      @Body request: JSONObject)

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: String,
                   @Body request: JSONObject)

    @DELETE("/bookmarks/{id}")
    fun deleteBookmark(@Path("id") id: String,
                       @Body request: JSONObject)

    @DELETE("/users")
    fun deleteUser(@Body request: DeleteUserRequest)
}