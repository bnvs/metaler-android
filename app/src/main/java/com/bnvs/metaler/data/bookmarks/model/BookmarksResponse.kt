package com.bnvs.metaler.data.bookmarks.model


data class BookmarksResponse(
    val post_count: Int,
    val is_next: Boolean,
    val posts: List<Bookmark>
)