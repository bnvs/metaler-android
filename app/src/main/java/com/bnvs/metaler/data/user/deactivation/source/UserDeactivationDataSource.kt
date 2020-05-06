package com.bnvs.metaler.data.user.deactivation.source

interface UserDeactivationDataSource {

    fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun logout(
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}