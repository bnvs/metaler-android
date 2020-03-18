package com.example.metaler_android.data.post.source

import com.example.metaler_android.data.post.Posts

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback)

}