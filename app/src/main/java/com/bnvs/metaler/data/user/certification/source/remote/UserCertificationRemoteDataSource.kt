package com.bnvs.metaler.data.user.certification.source.remote

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.UserCertificationDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
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
                if (response.isSuccessful) {
                    callback.onUserAdded(response.body()!!)

                } else {
                    callback.onResponseError(response.errorBody().toString())
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
                    if (response.isSuccessful) {
                        callback.onMembershipChecked(response.body()!!)

                    } else {
                        callback.onResponseError(response.errorBody().toString())
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
                    if (response.isSuccessful) {
                        callback.onLoginSuccess(response.body()!!)

                    } else {
                        callback.onResponseError(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }
}