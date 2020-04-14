package com.bnvs.metaler.data.user.modification.source.remote

import com.bnvs.metaler.data.user.modification.model.*
import com.bnvs.metaler.data.user.modification.source.UserModificationDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object UserModificationRemoteDataSource : UserModificationDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getUserJob(
        onSuccess: (response: Jobs) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getUserJob().enqueue(object : Callback<Jobs> {
            override fun onResponse(call: Call<Jobs>, response: Response<Jobs>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<Jobs>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun modifyUserJob(
        request: Job,
        onSuccess: (response: ModifyJobResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.modifyUserJob(request).enqueue(object : Callback<ModifyJobResponse> {
            override fun onResponse(
                call: Call<ModifyJobResponse>,
                response: Response<ModifyJobResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<ModifyJobResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun modifyNickname(
        request: Nickname,
        onSuccess: (response: ModifyNicknameResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.modifyNickname(request).enqueue(object : Callback<ModifyNicknameResponse> {
            override fun onResponse(
                call: Call<ModifyNicknameResponse>,
                response: Response<ModifyNicknameResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<ModifyNicknameResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}