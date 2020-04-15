package com.bnvs.metaler.data.comments.source.remote

import com.bnvs.metaler.data.comments.*
import com.bnvs.metaler.data.comments.source.CommentsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object CommentsRemoteDataSource : CommentsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getComments(
        postId: Int,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getComments(postId).enqueue(object : Callback<Comments> {
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
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: EditCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.modifyComment(commentId, request)
            .enqueue(object : Callback<EditCommentResponse> {
                override fun onResponse(
                    call: Call<EditCommentResponse>,
                    response: Response<EditCommentResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<EditCommentResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    override fun deleteComment(
        commentId: Int,
        onSuccess: (response: DeleteCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.deleteComment(commentId).enqueue(object : Callback<DeleteCommentResponse> {
            override fun onResponse(
                call: Call<DeleteCommentResponse>,
                response: Response<DeleteCommentResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<DeleteCommentResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}