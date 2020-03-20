package com.example.metaler_android.data.post.source.remote

import com.example.metaler_android.data.post.Posts
import com.example.metaler_android.data.post.source.PostDataSource
import com.example.metaler_android.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRemoteDataSource : PostDataSource{
    private val retrofitClient = RetrofitClient.client

    // 임시로 작성한 json object
    var json: JSONObject = JSONObject()

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        retrofitClient.getPosts("material", json).enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                val posts: Posts? = response.body()
                if (posts != null) {
                    callback.onPostsLoaded(posts)
                }else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
            }

        })
    }
}