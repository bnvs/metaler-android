package com.bnvs.metaler.data.bookmarks


data class BookmarksResponse(
    val count: Int,
    val is_next: Boolean,
    val posts: List<Bookmark>,
    val code: String
)