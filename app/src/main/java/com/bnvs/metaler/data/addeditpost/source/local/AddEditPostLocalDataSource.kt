package com.bnvs.metaler.data.addeditpost.source.local

import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache

interface AddEditPostLocalDataSource {

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