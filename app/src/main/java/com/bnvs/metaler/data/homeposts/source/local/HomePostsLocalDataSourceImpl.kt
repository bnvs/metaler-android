package com.bnvs.metaler.data.homeposts.source.local

import android.content.Context
import com.bnvs.metaler.data.homeposts.model.HomePosts
import com.bnvs.metaler.util.constants.LOCAL_HOME_POST_DATA

class HomePostsLocalDataSourceImpl(context: Context) : HomePostsLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_HOME_POST_DATA, Context.MODE_PRIVATE)

    override fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}