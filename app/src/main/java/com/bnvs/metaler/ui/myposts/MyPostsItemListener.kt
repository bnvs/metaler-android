package com.bnvs.metaler.ui.myposts

import android.view.View

interface MyPostsItemListener {
    fun onPostClick(clickerPostId: Int)
    fun onMoreButtonClick(view: View, clickedPostId: Int, position: Int)
}