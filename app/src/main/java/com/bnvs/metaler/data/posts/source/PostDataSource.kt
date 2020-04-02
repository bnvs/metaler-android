package com.bnvs.metaler.data.posts.source

import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(postsResponse: PostsResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    fun getPosts(access_token: String, request: PostsRequest, callback: LoadPostsCallback)

}