package com.bnvs.metaler.data.tags.source.repository

import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.source.remote.TagsRemoteDataSource

class TagsRepositoryImpl(
    private val tagsRemoteDataSource: TagsRemoteDataSource
) : TagsRepository {
    override fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: List<String>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        tagsRemoteDataSource.getTagRecommendations(request, onSuccess, onFailure)
    }
}