package com.bnvs.metaler.data.user.deactivation.source.repository

interface UserDeactivationRepository {

    fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}