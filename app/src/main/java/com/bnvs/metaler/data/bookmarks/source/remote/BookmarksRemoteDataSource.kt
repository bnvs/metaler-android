package com.bnvs.metaler.data.bookmarks.source.remote

import com.bnvs.metaler.data.bookmarks.*
import com.bnvs.metaler.data.bookmarks.source.BookmarksDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object BookmarksRemoteDataSource : BookmarksDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun addBookmark(
        request: AddBookmarkRequest,
        onSuccess: (response: AddBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
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
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<AddBookmarkResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun deleteBookmark(
        bookmarkId: Int,
        onSuccess: (response: DeleteBookmarkResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.deleteBookmark(bookmarkId)
            .enqueue(object : Callback<DeleteBookmarkResponse> {
                override fun onResponse(
                    call: Call<DeleteBookmarkResponse>,
                    response: Response<DeleteBookmarkResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<DeleteBookmarkResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    override fun getMyBookmarks(
        request: BookmarksRequest,
        onSuccess: (response: BookmarksResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getMyBookmarks(request.page, request.limit)
            .enqueue(object : Callback<BookmarksResponse> {
                override fun onResponse(
                    call: Call<BookmarksResponse>,
                    response: Response<BookmarksResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<BookmarksResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}