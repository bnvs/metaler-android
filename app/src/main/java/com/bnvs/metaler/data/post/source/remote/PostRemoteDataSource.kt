package com.bnvs.metaler.data.post.source.remote

import com.bnvs.metaler.data.post.Posts
import com.bnvs.metaler.data.post.PostsCategory
import com.bnvs.metaler.data.post.PostsRequest
import com.bnvs.metaler.data.post.source.PostDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostRemoteDataSource : PostDataSource{

    private val retrofitClient = RetrofitClient.client

    // 임시로 작성한 매개변수
    var pc: PostsCategory = PostsCategory(
        "d",
        1,
        5,
        "asdf",
        "asdf")
    var postsCategory = listOf(pc)
    var json: PostsRequest = PostsRequest("asdfa", postsCategory)

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