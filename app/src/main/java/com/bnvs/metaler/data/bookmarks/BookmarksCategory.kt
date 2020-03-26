package com.bnvs.metaler.data.bookmarks

data class BookmarksCategory(
    val type: String,
    val page: Int,
    val limit: Int,
    val search_type: String,
    val search_word: String
)