package com.bnvs.metaler.view.bookmarks.recyclerview

interface BookmarkClickListener {
    fun onPostClick(postId: Int)
    fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int)
}