package com.example.metaler_android.data.post.source.remote

import com.example.metaler_android.data.post.Posts
import com.example.metaler_android.data.post.source.PostDataSource
import com.example.metaler_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRemoteDataSource : PostDataSource{
    private val retrofitClient = RetrofitClient.client

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        retrofitClient.getMaterialPosts().enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                val posts: Posts? = response.body()
                callback.onPostsLoaded(posts!!)
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                callback.onDataNotAvailable()
            }

        })
    }
}