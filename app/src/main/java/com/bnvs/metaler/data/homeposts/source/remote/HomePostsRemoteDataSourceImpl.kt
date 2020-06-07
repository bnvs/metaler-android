package com.bnvs.metaler.data.homeposts.source.remote

import com.bnvs.metaler.data.homeposts.model.HomePosts
import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class HomePostsRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : HomePostsRemoteDataSource {

    override fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.getHomePosts().enqueue(object : Callback<HomePosts> {
            override fun onResponse(
                call: Call<HomePosts>,
                response: Response<HomePosts>
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

            override fun onFailure(call: Call<HomePosts>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}