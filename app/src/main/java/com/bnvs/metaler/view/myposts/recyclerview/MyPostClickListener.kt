package com.bnvs.metaler.view.myposts.recyclerview

interface MyPostClickListener {
    fun onPostClick(postId: Int)
    fun onMoreButtonClick(postId: Int, position: Int)
}