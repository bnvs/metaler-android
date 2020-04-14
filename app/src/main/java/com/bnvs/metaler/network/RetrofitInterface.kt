package com.bnvs.metaler.network

import com.bnvs.metaler.data.addeditpost.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.AddPostResponse
import com.bnvs.metaler.data.addeditpost.DeletePostResponse
import com.bnvs.metaler.data.addeditpost.EditPostResponse
import com.bnvs.metaler.data.bookmarks.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.BookmarksResponse
import com.bnvs.metaler.data.categories.Categories
import com.bnvs.metaler.data.comments.AddCommentRequest
import com.bnvs.metaler.data.comments.Comments
import com.bnvs.metaler.data.homeposts.HomePosts
import com.bnvs.metaler.data.myposts.MyPosts
import com.bnvs.metaler.data.postdetails.PostDetails
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.deactivation.model.DeleteUserResponse
import com.bnvs.metaler.data.user.modification.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 해당 interface 에 기술된 명세들은
 * retrofit 을 사용하여 HTTP API 로 전환된다.
 * 반환되는 값은 Call<객체타입> 형태로 작성
 *
 *          <목차> - ctrl + F 로 검색하세요
 *          1. 유저
 *              1-1. 인증관련
 *              1-2. 회원정보수정
 *          2. 게시글
 *              2-1. 카테고리
 *              2-2. 글
 *              2-3. 댓글
 *              2-4. 북마크
 *              2-5. 홈
 *              2-6. 내가 쓴 글
 *          3. 파일
 * */

interface RetrofitInterface {

    /*** [1. 유저] ***/
    /*** [1-1. 인증관련] ***/
    // 회원가입 여부 체크
    @POST("/users/check")
    fun checkUserMembership(@Body request: CheckMembershipRequest): Call<CheckMembershipResponse>

    // 회원가입
    @POST("/users/join")
    fun addUser(@Body request: AddUserRequest): Call<AddUserResponse>

    // 로그인
    @POST("/users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    /*** [1-2. 회원정보 수정] ***/
    // 소속조회
    @GET("/users/jobs")
    fun getUserJob(): Call<Jobs>

    // 소속수정
    @PUT("/users/jobs")
    fun modifyUserJob(
        @Body request: Job
    ): Call<ModifyJobResponse>

    // 닉네임 수정
    @PUT("/users/nickname")
    fun modifyNickname(
        @Body request: Nickname
    ): Call<ModifyNicknameResponse>

    // 회원탈퇴
    @DELETE("/users")
    fun deleteUser(): Call<DeleteUserResponse>


    /*** [2. 게시글] ***/
    /*** [2-1. 카테고리] ***/
    // 카테고리 조회 (재료, 가공)
    @GET("/categories")
    fun getCategories(): Call<Categories>

    /*** [2-2. 글] ***/
    // 게시글 목록 조회
    @GET("/posts")
    fun getPosts(
        @QueryMap options: MutableMap<String, Any>
    ): Call<PostsResponse>

    // 게시글 상세 조회
    @GET("/posts/{id}")
    fun getPostDetails(
        @Path("id") post_id: Int
    ): Call<PostDetails>

    // 게시글 작성
    @POST("/posts")
    fun addPost(
        @Body request: AddEditPostRequest
    ): Call<AddPostResponse>

    // 게시글 수정
    @PUT("/posts/{id}")
    fun modifyPost(
        @Path("id") post_id: Int,
        @Body request: AddEditPostRequest
    ): Call<EditPostResponse>

    // 게시글 삭제
    @DELETE("/posts/{id}")
    fun deletePost(
        @Path("id") post_id: Int
    ): Call<DeletePostResponse>

    /*** [2-3. 댓글] ***/
    // 댓글 목록 조회
    @GET("/posts/{id}/comments")
    fun getComments(
        @Path("id") post_id: Int
    ): Call<Comments>

    // 댓글 추가
    @POST("/posts/{id}/comments")
    fun addComment(
        @Path("id") post_id: Int,
        @Body request: AddCommentRequest
    ): Call<ResponseBody>

    // 댓글 수정
    @PUT("comments/{id}")
    fun modifyComment(
        @Path("id") comment_id: Int,
        @Body request: AddCommentRequest
    ): Call<ResponseBody>

    // 댓글 삭제
    @DELETE("comments/{id}")
    fun deleteComment(
        @Path("id") comment_id: Int
    ): Call<ResponseBody>

    /*** [2-4. 북마크] ***/
    // 북마크 추가
    @POST("/users/bookmarks")
    fun addBookmark(
        @Body request: AddBookmarkRequest
    ): Call<ResponseBody>

    // 북마크 삭제
    @DELETE("/users/bookmarks/{id}")
    fun deleteBookmark(
        @Path("id") bookmark_id: Int
    ): Call<ResponseBody>

    // 북마크 목록 조회
    @GET("/users/bookmarks")
    fun getBookmarks(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<BookmarksResponse>

    /*** [2-5. 홈] ***/
    // 홈 게시글 조회
    @GET("/homes")
    fun getHomePosts(
    ): Call<HomePosts>

    /*** [2-6. 내가쓴글] ***/
    // 내가 쓴 글 조회
    @GET("/users/posts")
    fun getMyPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String
    ): Call<MyPosts>


    /*** [3. 파일] ***/
    // 파일 업로드
    @Multipart
    @POST("/uploadFile.php")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    // 파일 다운로드
    @GET("/downloadFile.php")
    fun getFile(
        @Query("url") url: String,
        @Query("name") name: String
    ): Call<ResponseBody>
}