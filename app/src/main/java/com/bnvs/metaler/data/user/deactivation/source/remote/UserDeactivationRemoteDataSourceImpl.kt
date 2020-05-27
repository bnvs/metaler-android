package com.bnvs.metaler.data.user.deactivation.source.remote

import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.network.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserDeactivationRemoteDataSourceImpl : UserDeactivationRemoteDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.deleteUser().enqueue(object : Callback<ResponseBody> {
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

    override fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.logout().enqueue(object : Callback<ResponseBody> {
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
}