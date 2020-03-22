package com.example.metaler_android.util

import android.view.View

interface PostItemListener {
    fun onPostClick(clickedPostId: Int)

    fun onBookmarkButtonClick(view: View, clickedPostId: Int, isBookmark: Boolean, position: Int)
}