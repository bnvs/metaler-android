package com.bnvs.metaler.data.user.deactivation.source.remote

import com.bnvs.metaler.data.user.deactivation.model.DeleteUserResponse
import com.bnvs.metaler.data.user.deactivation.source.UserDeactivationDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object UserDeactivationRemoteDataSource : UserDeactivationDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun deleteUser(
        onSuccess: (response: DeleteUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.deleteUser().enqueue(object : Callback<DeleteUserResponse> {
            override fun onResponse(
                call: Call<DeleteUserResponse>,
                response: Response<DeleteUserResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun logout() {}
}