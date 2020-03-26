package com.bnvs.metaler_android.data.bookmarks

data class BookmarksRequest(
    val access_token: String,
    val bookmark: List<BookmarksCategory>
)