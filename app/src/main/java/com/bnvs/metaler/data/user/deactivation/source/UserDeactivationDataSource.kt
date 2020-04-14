package com.bnvs.metaler.data.user.deactivation.source

import com.bnvs.metaler.data.user.deactivation.model.DeleteUserResponse

interface UserDeactivationDataSource {

    fun deleteUser(
        onSuccess: (response: DeleteUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun logout()

}