package com.bnvs.metaler.data.user.deactivation.source

import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSource

class UserDeactivationRepository : UserDeactivationDataSource {

    private val userRemoteDataSource = UserDeactivationRemoteDataSource

    override fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.deleteUser(onSuccess, onFailure)
    }

    override fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.logout(onSuccess, onFailure)
    }
}