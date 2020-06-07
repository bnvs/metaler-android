package com.bnvs.metaler.data.myposts.source.local

import android.content.Context
import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.util.constants.LOCAL_MY_POSTS_DATA

class MyPostsLocalDataSourceImpl(context: Context) : MyPostsLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_MY_POSTS_DATA, Context.MODE_PRIVATE)

    override fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}