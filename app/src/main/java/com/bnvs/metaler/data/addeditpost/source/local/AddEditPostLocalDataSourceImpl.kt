package com.bnvs.metaler.data.addeditpost.source.local

import android.content.Context
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.AddPostResponse
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import com.bnvs.metaler.util.constants.LOCAL_ADD_POST_DATA
import com.bnvs.metaler.util.constants.LOCAL_EDIT_POST_DATA
import okhttp3.MultipartBody

class AddEditPostLocalDataSourceImpl(context: Context) : AddEditPostLocalDataSource {

    private val sharedAddPost =
        context.getSharedPreferences(LOCAL_ADD_POST_DATA, Context.MODE_PRIVATE)

    private val sharedEditPost =
        context.getSharedPreferences(LOCAL_EDIT_POST_DATA, Context.MODE_PRIVATE)

    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}