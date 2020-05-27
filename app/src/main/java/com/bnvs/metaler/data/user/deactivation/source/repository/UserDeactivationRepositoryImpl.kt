package com.bnvs.metaler.data.user.deactivation.source.repository

import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSource

class UserDeactivationRepositoryImpl(
    private val userDeactivationRemoteDataSource: UserDeactivationRemoteDataSource
) : UserDeactivationRepository {

    override fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userDeactivationRemoteDataSource.deleteUser(onSuccess, onFailure)
    }

    override fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userDeactivationRemoteDataSource.logout(onSuccess, onFailure)
    }

}