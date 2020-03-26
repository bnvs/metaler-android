package com.bnvs.metaler.data.post.source

import com.bnvs.metaler.data.post.Posts

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback)

}