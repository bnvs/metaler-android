package com.bnvs.metaler.util.newadpaters

interface PostClickListener {
    fun onPostClick(postId: Int)
    fun addBookmarkButtonClick(postId: Int, position: Int)
    fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int)
}