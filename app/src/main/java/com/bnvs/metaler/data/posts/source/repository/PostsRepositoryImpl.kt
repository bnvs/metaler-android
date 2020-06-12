package com.bnvs.metaler.data.posts.source.repository

import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.data.posts.source.local.PostsLocalDataSource
import com.bnvs.metaler.data.posts.source.remote.PostsRemoteDataSource

class PostsRepositoryImpl(
    private val postsLocalDataSource: PostsLocalDataSource,
    private val postsRemoteDataSource: PostsRemoteDataSource
) : PostsRepository {

    override fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        postsRemoteDataSource.getPosts(request, onSuccess, onFailure, handleError)
    }

    override fun getPostsWithSearchTypeContent(
        request: PostsWithContentRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        postsRemoteDataSource.getPostsWithSearchTypeContent(
            request,
            onSuccess,
            onFailure,
            handleError
        )
    }

    override fun getPostsWithSearchTypeTag(
        request: PostsWithTagRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        postsRemoteDataSource.getPostsWithSearchTypeTag(request, onSuccess, onFailure, handleError)
    }

}