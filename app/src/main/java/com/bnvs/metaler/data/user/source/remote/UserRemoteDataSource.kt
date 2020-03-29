package com.bnvs.metaler.data.user.source.remote

import android.util.Log
import com.bnvs.metaler.data.user.*
import com.bnvs.metaler.data.user.source.UserDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRemoteDataSource : UserDataSource {

    private val TAG = "UserRemoteDataSource"
    private val retrofitClient = RetrofitClient.client

    override fun addUser(
        request: AddUserRequest,
        callback: UserDataSource.AddUserCallback
    ) {
        retrofitClient.addUser(request).
            enqueue(object : Callback<AddUserResponse> {
                override fun onResponse(
                    call: Call<AddUserResponse>,
                    response: Response<AddUserResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "회원가입 api 응답 : $response")
                        Log.d(TAG, "회원가입 api 응답 body : ${response.body()}")
                        callback.onUserAdded(response.body()!!)

                    }else {
                        callback.onResponseError(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }

    override fun deleteUser(callback: UserDataSource.DeleteUserCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        callback: UserDataSource.CheckMembershipCallback
    ) {
        retrofitClient.checkUserMembership(request)
            .enqueue(object : Callback<CheckMembershipResponse> {
                override fun onResponse(
                    call: Call<CheckMembershipResponse>,
                    response: Response<CheckMembershipResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "회원가입 여부 확인 api 응답 : $response")
                        Log.d(TAG, "회원가입 여부 확인 api 응답 body : ${response.body()}")
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
        callback: UserDataSource.LoginCallback
    ) {
        retrofitClient.login(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "로그인 api 응답 : $response")
                        Log.d(TAG, "로그인 api 응답 body : ${response.body()}")
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