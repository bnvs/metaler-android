package com.bnvs.metaler.data.postdetails.source.remote

import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.source.PostDetailsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object PostDetailsRemoteDataSource : PostDetailsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getPostDetails(postId).enqueue(object : Callback<PostDetails> {
            override fun onResponse(call: Call<PostDetails>, response: Response<PostDetails>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<PostDetails>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}