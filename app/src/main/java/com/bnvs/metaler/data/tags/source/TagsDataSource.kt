package com.bnvs.metaler.data.tags.source

import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.model.TagsResponse

interface TagsDataSource {
    fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: TagsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}