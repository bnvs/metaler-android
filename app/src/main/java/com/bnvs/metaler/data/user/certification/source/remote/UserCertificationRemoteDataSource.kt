package com.bnvs.metaler.data.user.certification.source.remote

import android.util.Log
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
        retrofitClient.addUser(request).
            enqueue(object : Callback<AddUserResponse> {
                override fun onResponse(
                    call: Call<AddUserResponse>,
                    response: Response<AddUserResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "회원가입 성공")
                        Log.d(TAG, "회원가입 api 응답 : $response")
                        Log.d(TAG, "회원가입 api 응답 body : ${response.body()}")
                        callback.onUserAdded(response.body()!!)

                    }else {
                        Log.d(TAG, "회원가입 실패")
                        Log.d(TAG, "회원가입 api 응답 : $response")
                        Log.d(TAG, "회원가입 api 응답 body : ${response.body()}")
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
                        Log.d(TAG, "회원가입 여부 확인 성공")
                        Log.d(TAG, "회원가입 여부 확인 api 응답 : $response")
                        Log.d(TAG, "회원가입 여부 확인 api 응답 body : ${response.body()}")
                        callback.onMembershipChecked(response.body()!!)

                    } else {
                        Log.d(TAG, "회원가입 여부 확인 실패")
                        Log.d(TAG, "회원가입 여부 확인 api 응답 : $response")
                        Log.d(TAG, "회원가입 여부 확인 api 응답 body : ${response.body()}")
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
                        Log.d(TAG, "로그인 성공")
                        Log.d(TAG, "로그인 api 응답 : $response")
                        Log.d(TAG, "로그인 api 응답 body : ${response.body()}")
                        callback.onLoginSuccess(response.body()!!)

                    } else {
                        Log.d(TAG, "로그인 실패")
                        Log.d(TAG, "로그인 api 응답 : $response")
                        Log.d(TAG, "로그인 api 응답 body : ${response.body()}")
                        callback.onResponseError(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }
}