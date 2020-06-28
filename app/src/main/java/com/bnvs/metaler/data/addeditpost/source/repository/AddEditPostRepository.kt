package com.bnvs.metaler.data.addeditpost.source.repository

import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.AddPostResponse
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import okhttp3.MultipartBody

interface AddEditPostRepository {
    fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun getAddPostCache(
        onSuccess: (response: AddEditPostLocalCache) -> Unit,
        onFailure: () -> Unit
    )

    fun saveAddPostCache(addPostLocalCache: AddEditPostLocalCache)

    fun getEditPostCache(
        postId: Int,
        onSuccess: (response: AddEditPostLocalCache) -> Unit,
        onFailure: () -> Unit
    )

    fun saveEditPostCache(postId: Int, editPostLocalCache: AddEditPostLocalCache)
}