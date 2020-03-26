package com.bnvs.metaler.data.bookmarks

data class BookmarksRequest(
    val access_token: String,
    val bookmark: List<BookmarksCategory>
)