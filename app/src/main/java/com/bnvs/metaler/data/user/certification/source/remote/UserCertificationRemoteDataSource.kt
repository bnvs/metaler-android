package com.bnvs.metaler.data.user.certification.source.remote

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.UserCertificationDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object UserCertificationRemoteDataSource : UserCertificationDataSource {

    private val TAG = "UserCertificationRemoteDataSource"
    private val retrofitClient = RetrofitClient.client

    override fun addUser(
        request: AddUserRequest,
        callback: UserCertificationDataSource.AddUserCallback
    ) {
        retrofitClient.addUser(request).enqueue(object : Callback<AddUserResponse> {
            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    callback.onUserAdded(body)
                } else {
                    callback.onResponseError(HttpException(response))
                }
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        callback: UserCertificationDataSource.CheckMembershipCallback
    ) {
        retrofitClient.checkUserMembership(request)
            .enqueue(object : Callback<CheckMembershipResponse> {
                override fun onResponse(
                    call: Call<CheckMembershipResponse>,
                    response: Response<CheckMembershipResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        callback.onMembershipChecked(body)
                    } else {
                        callback.onResponseError(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<CheckMembershipResponse>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }

    override fun login(
        request: LoginRequest,
        callback: UserCertificationDataSource.LoginCallback
    ) {
        retrofitClient.login(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        callback.onLoginSuccess(body)
                    } else {
                        callback.onResponseError(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }
}