package com.bnvs.metaler.data.user.source.remote

import android.util.Log
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.user.CheckMembershipRequest
import com.bnvs.metaler.data.user.CheckMembershipResponse
import com.bnvs.metaler.data.user.source.UserDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRemoteDataSource : UserDataSource{

    private val TAG = "UserRemoteDataSource"
    private val retrofitClient = RetrofitClient.client

    override fun addUser(callback: UserDataSource.AddUserCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(callback: UserDataSource.DeleteUserCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkMembership(kakao_id: String, callback: UserDataSource.CheckMembershipCallback) {
        retrofitClient.checkUserMembership(CheckMembershipRequest(kakao_id))
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

                override fun onFailure(
                    call: Call<CheckMembershipResponse>,
                    t: Throwable
                ) {
                    callback.onFailure(t)
                }
            })
    }

    override fun login(callback: UserDataSource.LoginCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}