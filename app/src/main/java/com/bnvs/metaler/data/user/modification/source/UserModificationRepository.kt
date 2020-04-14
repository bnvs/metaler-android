package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Jobs
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSource
import okhttp3.ResponseBody

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
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.modifyUserJob(request, onSuccess, onFailure)
    }

    override fun modifyNickname(
        request: Nickname,
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.modifyNickname(request, onSuccess, onFailure)
    }
}