package com.bnvs.metaler.data.user.deactivation.source

import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSource
import okhttp3.ResponseBody

class UserDeactivationRepository : UserDeactivationDataSource {

    private val userRemoteDataSource = UserDeactivationRemoteDataSource

    override fun deleteUser(
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.deleteUser(onSuccess, onFailure)
    }

    override fun logout() {}
}