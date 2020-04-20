package com.bnvs.metaler.data.homeposts.source.local

import android.content.Context
import com.bnvs.metaler.data.homeposts.HomePosts
import com.bnvs.metaler.data.homeposts.source.HomePostsDataSource

class HomePostsLocalDataSource(context: Context) : HomePostsDataSource {

    private val sharedPreferences = context.getSharedPreferences("homePosts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}