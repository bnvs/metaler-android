package com.bnvs.metaler.data.tags.source

import com.bnvs.metaler.data.tags.model.TagsRequest

interface TagsDataSource {
    fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: List<String>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}