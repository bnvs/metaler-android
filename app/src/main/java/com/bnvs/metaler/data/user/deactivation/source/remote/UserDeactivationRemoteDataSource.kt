package com.bnvs.metaler.data.user.deactivation.source.remote

interface UserDeactivationRemoteDataSource {

    fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}