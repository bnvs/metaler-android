package com.bnvs.metaler.view.posts.recyclerview.listener

interface PostClickListener {
    fun onPostClick(postId: Int)
    fun addBookmarkButtonClick(postId: Int, position: Int)
    fun deleteBookmarkButtonClick(bookmarkId: Int, position: Int)
}