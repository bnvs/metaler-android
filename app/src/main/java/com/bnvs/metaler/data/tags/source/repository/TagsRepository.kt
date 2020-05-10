package com.bnvs.metaler.data.tags.source.repository

import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.model.TagsResponse
import com.bnvs.metaler.data.tags.source.TagsDataSource
import com.bnvs.metaler.data.tags.source.remote.TagsRemoteDataSource

class TagsRepository : TagsDataSource {

    private val tagsRemoteDataSource = TagsRemoteDataSource

    override fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: TagsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        tagsRemoteDataSource.getTagRecommendations(request, onSuccess, onFailure)
    }
}