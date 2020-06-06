package com.bnvs.metaler.data.tags.source.remote

import com.bnvs.metaler.data.tags.model.TagsRequest

interface TagsRemoteDataSource {
    fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: List<String>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}