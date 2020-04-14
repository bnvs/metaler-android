package com.bnvs.metaler.data.user.deactivation.source.remote

import com.bnvs.metaler.data.user.deactivation.source.UserDeactivationDataSource
import com.bnvs.metaler.network.RetrofitClient
import okhttp3.ResponseBody

object UserDeactivationRemoteDataSource : UserDeactivationDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun deleteUser(
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.deleteUser()
    }

    override fun logout() {}
}