package com.bnvs.metaler.data.tags.source.repository

import com.bnvs.metaler.data.tags.model.TagsRequest

interface TagsRepository {
    fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: List<String>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}