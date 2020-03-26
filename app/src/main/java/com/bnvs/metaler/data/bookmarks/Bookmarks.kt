package com.bnvs.metaler.data.bookmarks

data class Bookmarks(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<Bookmark>
)