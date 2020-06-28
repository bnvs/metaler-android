package com.bnvs.metaler.data.bookmarks.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

data class Bookmark(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val liked: Int,
    val disliked: Int,
    val thumbnail: String,
    val tags: List<PostTag>,
    val bookmark_id: Int
)