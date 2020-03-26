package com.bnvs.metaler_android.data.homeposts.source.local

import android.content.Context
import com.bnvs.metaler_android.data.homeposts.source.HomePostsDataSource

class HomePostsLocalDataSource(context: Context) : HomePostsDataSource {

    private val sharedPreferences = context.getSharedPreferences("homePosts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getHomePosts(callback: HomePostsDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}