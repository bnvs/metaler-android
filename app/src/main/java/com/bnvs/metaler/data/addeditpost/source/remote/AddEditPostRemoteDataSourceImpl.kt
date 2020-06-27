package com.bnvs.metaler.data.addeditpost.source.remote

import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.AddPostResponse
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class AddEditPostRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : AddEditPostRemoteDataSource {

    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.addPost(request).enqueue(object : Callback<AddPostResponse> {
            override fun onResponse(
                call: Call<AddPostResponse>,
                response: Response<AddPostResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    val e = ErrorHandler.getErrorCode(HttpException(response))
                    if (e != NO_ERROR_TO_HANDLE) {
                        handleError(e)
                    }
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.modifyPost(postId, request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    val e = ErrorHandler.getErrorCode(HttpException(response))
                    if (e != NO_ERROR_TO_HANDLE) {
                        handleError(e)
                    }
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.uploadFile(file).enqueue(object : Callback<UploadFileResponse> {
            override fun onResponse(
                call: Call<UploadFileResponse>,
                response: Response<UploadFileResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    val e = ErrorHandler.getErrorCode(HttpException(response))
                    if (e != NO_ERROR_TO_HANDLE) {
                        handleError(e)
                    }
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<UploadFileResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

}