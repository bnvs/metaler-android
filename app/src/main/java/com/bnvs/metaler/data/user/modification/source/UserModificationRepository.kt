package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.*
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSource

class UserModificationRepository : UserModificationDataSource {

    private val userRemoteDataSource =
        UserModificationRemoteDataSource

    override fun getUserJob(
        onSuccess: (response: Jobs) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.getUserJob(onSuccess, onFailure)
    }

    override fun modifyUserJob(
        request: Job,
        onSuccess: (response: ModifyJobResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.modifyUserJob(request, onSuccess, onFailure)
    }

    override fun modifyNickname(
        request: Nickname,
        onSuccess: (response: ModifyNicknameResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.modifyNickname(request, onSuccess, onFailure)
    }
}