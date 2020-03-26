package com.bnvs.metaler_android.data.post.source

import com.bnvs.metaler_android.data.post.Posts

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback)

}