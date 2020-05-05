package com.bnvs.metaler.data.bookmarks.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

data class Bookmark(
    val post_id: Int,
    val title: String,
    val content: String,
    val profile_nickname: Int,
    val date: String,
    val liked: Int,
    val dis_liked: Int,
    val thumbnail: String,
    val tags: List<PostTag>,
    val is_bookmark: Int
)