package com.bnvs.metaler.data.bookmarks.model

data class BookmarksRequest(
    val category_type: String,
    val page: Int,
    val limit: Int
)