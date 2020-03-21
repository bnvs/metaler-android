package com.example.metaler_android.data.homepost.source.local

import android.content.Context
import com.example.metaler_android.data.homepost.source.HomePostDataSource

class HomePostsLocalDataSource(context: Context) : HomePostDataSource {

    private val sharedPreferences = context.getSharedPreferences("homePosts", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getHomePosts(callback: HomePostDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}