package com.example.metaler_android.data.bookmark

data class Bookmarks(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<Bookmark>
)