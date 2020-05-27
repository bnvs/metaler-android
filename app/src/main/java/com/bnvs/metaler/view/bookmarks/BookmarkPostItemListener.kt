package com.bnvs.metaler.view.bookmarks

import android.view.View

interface BookmarkPostItemListener {
    fun onPostClick(view: View, clickedPostId: Int)
    fun onDeleteButtonClick(view: View, bookmarkId: Int, position: Int)
}