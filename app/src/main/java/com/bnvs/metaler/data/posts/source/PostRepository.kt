package com.bnvs.metaler.data.posts.source

import android.content.Context
import com.bnvs.metaler.data.posts.source.local.PostLocalDataSource
import com.bnvs.metaler.data.posts.source.remote.PostRemoteDataSource

class PostRepository(context: Context) : PostDataSource {

    private val postLocalDataSource = PostLocalDataSource(context)
    private val postRemoteDataSource = PostRemoteDataSource

    override fun getPosts(callback: PostDataSource.LoadPostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}