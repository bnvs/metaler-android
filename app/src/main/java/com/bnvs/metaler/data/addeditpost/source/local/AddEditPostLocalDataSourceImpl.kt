package com.bnvs.metaler.data.addeditpost.source.local

import android.content.Context
import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache
import com.bnvs.metaler.util.constants.ADD_POST_DATA
import com.bnvs.metaler.util.constants.EDIT_POST_DATA
import com.bnvs.metaler.util.constants.LOCAL_ADD_POST_DATA
import com.bnvs.metaler.util.constants.LOCAL_EDIT_POST_DATA
import com.google.gson.GsonBuilder

class AddEditPostLocalDataSourceImpl(context: Context) : AddEditPostLocalDataSource {

    private val sharedAddPost =
        context.getSharedPreferences(LOCAL_ADD_POST_DATA, Context.MODE_PRIVATE)

    private val sharedEditPost =
        context.getSharedPreferences(LOCAL_EDIT_POST_DATA, Context.MODE_PRIVATE)

    override fun getAddPostCache(
        onSuccess: (response: AddEditPostLocalCache) -> Unit,
        onFailure: () -> Unit
    ) {
        val addPostLocalCache = sharedAddPost.getString(ADD_POST_DATA, null)
        if (addPostLocalCache != null) {
            onSuccess(
                GsonBuilder().create()
                    .fromJson(addPostLocalCache, AddEditPostLocalCache::class.java)
            )
        } else {
            onFailure()
        }
    }

    override fun saveAddPostCache(addEditPostLocalCache: AddEditPostLocalCache) {
        sharedAddPost.edit()
            .putString(ADD_POST_DATA, GsonBuilder().create().toJson(addEditPostLocalCache))
            .commit()
    }

    override fun getEditPostCache(
        postId: Int,
        onSuccess: (response: AddEditPostLocalCache) -> Unit,
        onFailure: () -> Unit
    ) {
        val editPostLocalCache = sharedAddPost.getString("${EDIT_POST_DATA}_$postId", null)
        if (editPostLocalCache != null) {
            onSuccess(
                GsonBuilder().create()
                    .fromJson(editPostLocalCache, AddEditPostLocalCache::class.java)
            )
        } else {
            onFailure()
        }
    }

    override fun saveEditPostCache(
        postId: Int,
        addEditPostLocalCache: AddEditPostLocalCache
    ) {
        sharedAddPost.edit()
            .putString(
                "${EDIT_POST_DATA}_$postId",
                GsonBuilder().create().toJson(addEditPostLocalCache)
            )
            .commit()
    }
}