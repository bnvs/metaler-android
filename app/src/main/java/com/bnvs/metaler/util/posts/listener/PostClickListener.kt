package com.bnvs.metaler.util.posts.listener

interface PostClickListener {
    fun onPostClick(postId: Int)
    fun addBookmarkButtonClick(postId: Int, position: Int)
    fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int)
}