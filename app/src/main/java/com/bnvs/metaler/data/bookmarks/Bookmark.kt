package com.bnvs.metaler.data.bookmarks

data class Bookmark(
    val post_id: Int,
    val title: String,
    val content: String,
    val profile_nickname: Int,
    val date: String,
    val liked: Int,
    val dis_liked: Int,
    val thumbnail: String,
    val tags: List<String>,
    val is_bookmark: Boolean
)