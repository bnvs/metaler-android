package com.bnvs.metaler.data.postdetails.source.local

import android.content.Context
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.data.postdetails.model.RatingRequest
import com.bnvs.metaler.util.constants.LOCAL_POST_DETAILS_DATA

class PostDetailsLocalDataSourceImpl(context: Context) : PostDetailsLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_POST_DETAILS_DATA, Context.MODE_PRIVATE)

    override fun getPostDetails(
        postId: Int,
        onSuccess: (response: PostDetails) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun deletePost(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun ratePost(
        postId: Int,
        request: RatingRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun unRatePost(postId: Int, onSuccess: () -> Unit, onFailure: (e: Throwable) -> Unit) {

    }
}