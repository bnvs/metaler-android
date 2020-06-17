package com.bnvs.metaler.data.bookmarks.source.remote

import com.bnvs.metaler.data.bookmarks.model.*
import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class BookmarksRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : BookmarksRemoteDataSource {

    override fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.addBookmark(request).enqueue(object : Callback<AddBookmarkResponse> {
            override fun onResponse(
                call: Call<AddBookmarkResponse>,
                response: Response<AddBookmarkResponse>
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

            override fun onFailure(call: Call<AddBookmarkResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun deleteBookmark(
        request: DeleteBookmarkRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.deleteBookmark(request.bookmark_id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
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

    override fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.getMyBookmarks(request.page, request.limit, request.category_type)
            .enqueue(object : Callback<BookmarksResponse> {
                override fun onResponse(
                    call: Call<BookmarksResponse>,
                    response: Response<BookmarksResponse>
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

                override fun onFailure(call: Call<BookmarksResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}