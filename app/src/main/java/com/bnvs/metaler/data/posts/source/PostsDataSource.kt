package com.bnvs.metaler.data.posts.source

import com.bnvs.metaler.data.posts.Post
import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse

interface PostsDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(postsResponse: PostsResponse): List<Post>
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    fun getPosts(access_token: String, request: PostsRequest, callback: LoadPostsCallback)

}