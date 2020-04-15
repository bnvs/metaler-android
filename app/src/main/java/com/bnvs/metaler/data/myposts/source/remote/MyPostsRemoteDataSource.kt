package com.bnvs.metaler.data.myposts.source.remote

import com.bnvs.metaler.data.myposts.MyPosts
import com.bnvs.metaler.data.myposts.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.MyPostsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object MyPostsRemoteDataSource : MyPostsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getMyPosts(request.page, request.limit, request.type).enqueue(object :
            Callback<MyPosts> {
            override fun onResponse(call: Call<MyPosts>, response: Response<MyPosts>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<MyPosts>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}