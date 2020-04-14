package com.bnvs.metaler.data.user.certification.source.remote

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.UserCertificationDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object UserCertificationRemoteDataSource : UserCertificationDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.addUser(request).enqueue(object : Callback<AddUserResponse> {
            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.checkUserMembership(request)
            .enqueue(object : Callback<CheckMembershipResponse> {
                override fun onResponse(
                    call: Call<CheckMembershipResponse>,
                    response: Response<CheckMembershipResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<CheckMembershipResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    override fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.login(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}