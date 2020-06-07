package com.bnvs.metaler.data.user.modification.source.remote

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserModificationRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : UserModificationRemoteDataSource {

    override fun getUserJob(
        onSuccess: (response: Job) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.getUserJob().enqueue(object : Callback<Job> {
            override fun onResponse(call: Call<Job>, response: Response<Job>) {
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

            override fun onFailure(call: Call<Job>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun modifyUserJob(
        request: Job,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.modifyUserJob(request).enqueue(object : Callback<ResponseBody> {
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

    override fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.modifyNickname(request).enqueue(object : Callback<ResponseBody> {
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

    override fun getTerms(
        onSuccess: (Terms) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.getTerms().enqueue(object : Callback<Terms> {
            override fun onResponse(call: Call<Terms>, response: Response<Terms>) {
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

            override fun onFailure(call: Call<Terms>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}