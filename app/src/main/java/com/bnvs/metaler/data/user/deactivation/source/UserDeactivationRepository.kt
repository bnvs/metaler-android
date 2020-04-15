package com.bnvs.metaler.data.user.deactivation.source

import com.bnvs.metaler.data.user.deactivation.model.DeleteUserResponse
import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSource

class UserDeactivationRepository : UserDeactivationDataSource {

    private val userRemoteDataSource = UserDeactivationRemoteDataSource

    override fun deleteUser(
        onSuccess: (response: DeleteUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.deleteUser(onSuccess, onFailure)
    }

    override fun logout() {}
}