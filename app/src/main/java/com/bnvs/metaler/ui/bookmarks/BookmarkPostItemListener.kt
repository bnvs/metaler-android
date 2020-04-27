package com.bnvs.metaler.ui.bookmarks

import android.view.View

interface BookmarkPostItemListener {
    fun onPostClick(clickedPostId: Int)
    fun onDeleteButtonClick(view: View, clickedPostId: Int, position: Int)
}