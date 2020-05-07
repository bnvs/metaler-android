package com.bnvs.metaler.data.comments.source.remote

import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.CommentsDataSource
import com.bnvs.metaler.network.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object CommentsRemoteDataSource : CommentsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getComments(
        postId: Int,
        request: CommentsRequest,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getComments(postId, request.page, request.limit)
            .enqueue(object : Callback<Comments> {
                override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<Comments>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    override fun addComment(
        postId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: AddCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.addComment(postId, request).enqueue(object : Callback<AddCommentResponse> {
            override fun onResponse(
                call: Call<AddCommentResponse>,
                response: Response<AddCommentResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<AddCommentResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun editComment(
        postId: Int,
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.modifyComment(postId, commentId, request)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    override fun deleteComment(
        postId: Int,
        commentId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.deleteComment(postId, commentId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun saveIsCommentModified(isCommentModified: Boolean) {}

    override fun isCommentModified(): Boolean {
        return false
    }
}