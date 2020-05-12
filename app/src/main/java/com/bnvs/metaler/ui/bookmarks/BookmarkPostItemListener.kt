package com.bnvs.metaler.ui.bookmarks

import android.view.View

interface BookmarkPostItemListener {
    fun onPostClick(view: View, clickedPostId: Int)
    fun onDeleteButtonClick(view: View, bookmarkId: Int, position: Int)
}