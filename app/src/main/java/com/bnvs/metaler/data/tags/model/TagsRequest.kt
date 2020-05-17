package com.bnvs.metaler.data.tags.model

data class TagsRequest(
    val type: Int,
    val name: String,
    val max: Int
)