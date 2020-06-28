package com.bnvs.metaler.view.myposts.recyclerview

import android.view.View

interface MyPostClickListener {
    fun onPostClick(postId: Int)
    fun onMoreButtonClick(
        view: View,
        clickedPostId: Int,
        likedNum: Int,
        dislikedNum: Int,
        position: Int
    )
}