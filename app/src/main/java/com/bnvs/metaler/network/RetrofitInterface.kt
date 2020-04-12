package com.bnvs.metaler.network

import com.bnvs.metaler.data.addeditpost.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.AddEditPostResponse
import com.bnvs.metaler.data.addeditpost.DeletePostResponse
import com.bnvs.metaler.data.bookmarks.*
import com.bnvs.metaler.data.categories.Categories
import com.bnvs.metaler.data.comments.AddCommentRequest
import com.bnvs.metaler.data.comments.Comments
import com.bnvs.metaler.data.homeposts.HomePosts
import com.bnvs.metaler.data.job.Jobs
import com.bnvs.metaler.data.postdetails.PostDetails
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.user.*
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

    /*** 1. 유저 - 인증관련 ***/
    // 회원가입 여부 체크
    @POST("/users/check")
    fun checkUserMembership(@Body request: CheckMembershipRequest): Call<CheckMembershipResponse>

    // 회원가입
    @POST("/users/join")
    fun addUser(@Body request: AddUserRequest): Call<AddUserResponse>

    // 로그인
    @POST("/users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    /*** 1. 유저 - 내가 쓴 글 ***/
    // 내가 쓴 글 조회
    @GET("/users/posts")
    fun getMyPosts()

    /*** 2. 북마크 ***/
    // 북마크 추가
    @POST("/users/bookmarks")
    fun addBookmark(
        @Header("Authorization") access_token: String,
        @Body request: AddBookmarkRequest
    ): Call<AddBookmarkResponse>

    // 북마크 삭제
    @DELETE("/users/bookmarks")
    fun deleteBookmark(
        @Header("Authorization") access_token: String,
        @Body request: DeleteBookmarkRequest
    ): Call<DeleteBookmarkResponse>

    // 북마크 목록 조회 (개발중)
    @GET("/users/bookmarks")
    fun getBookmarks(
        @Header("Authorization") access_token: String,
        @Part("type") type: String,
        @Part("page") page: Int,
        @Part("limit") limit: Int
    ): Call<BookmarksResponse>


    /*** 3. 게시글 ***/
    // 카테고리 (재료, 가공)
    @GET("/categories")
    fun getCategories(@Header("Authorization") access_token: String): Call<Categories>

    // 게시글 목록 조회 (태그검색은 개발중)
    @GET("/posts")
    fun getPosts(
        @Header("Authorization") access_token: String,
        @QueryMap options: Map<String, Int>
    ): Call<PostsResponse>

    // 게시글 상세 조회
    @GET("/posts/{id}")
    fun getPostDetails(
        @Header("Authorization") access_token: String,
        @Path("id") id: String
    ): Call<PostDetails>

    // 게시글 추가 (글쓰기)
    @POST("/posts")
    fun addPost(
        @Header("Authorization") access_token: String,
        @Body request: AddEditPostRequest
    ): Call<AddEditPostResponse>

    // 게시글 삭제
    @DELETE("/posts/{id}")
    fun deletePost(
        @Header("Authorization") access_token: String,
        @Path("id") id: String
    ): Call<DeletePostResponse>

    // 게시글 수정
    @PUT("/posts/{id}")
    fun modifyPost(
        @Header("Authorization") access_token: String,
        @Body request: AddEditPostRequest
    ): Call<AddEditPostResponse>


    /*** 3. 댓글 ***/
    // 댓글 조회
    @GET("/posts/{id}/comments/{page}/{limit}")
    fun getComments(
        @Header("Authorization") access_token: String,
        @Path("id") id: String,
        @Path("page") page: String,
        @Path("limit") limit: String
    ): Call<Comments>

    // 댓글 추가
    @POST("/posts/{id}/comments")
    fun addComment(
        @Header("Authorization") access_token: String,
        @Path("id") id: String,
        @Body request: AddCommentRequest
    )

    // 댓글 수정
    @PUT("/posts/{pid}/comments/{cid}")
    fun modifyComment(
        @Header("Authorization") access_token: String,
        @Path("pid") pid: String,
        @Path("cid") cid: String,
        @Body request: AddCommentRequest
    )

    // 댓글 삭제
    @DELETE("/posts/{pid}/comments/{cid}")
    fun deleteComment(
        @Header("Authorization") access_token: String,
        @Path("pid") pid: String,
        @Path("cid") cid: String
    )


    /*** 4. 파일 ***/
    // 파일 업로드
    @Multipart
    @POST("/uploadFile.php")
    fun addFile(
        @Part("file") file: RequestBody,
        @Part imageFile: MultipartBody.Part
    ): Call<JSONObject>

    // 파일 다운로드
    @GET("/downloadFile.php")
    fun getFile(
        @Path("url") url: String,
        @Path("name") name: String
    )


    /*** 5. 기타 ***/
    // 서버 상태 확인
    @GET("/ping")
    fun getPing()


    /*** 6. 미완성 api ***/
    @GET("/home")
    fun getHomePosts(@Body request: JSONObject): Call<HomePosts>

    @GET("/users/jobs")
    fun getJob(@Body request: JSONObject): Call<Jobs>

    @PUT("/users/jobs")
    fun modifyJob(@Body request: Jobs)

    @PUT("/users/nickname")
    fun modifyNickname()

    @DELETE("/users")
    fun deleteUser(@Body request: DeleteUserRequest)
}