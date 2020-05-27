package com.bnvs.metaler.data.user.modification.source.repository

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSource
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSource

class UserModificationRepositoryImpl(
    private val userModificationLocalDataSource: UserModificationLocalDataSource,
    private val userModificationRemoteDataSource: UserModificationRemoteDataSource
) : UserModificationRepository {

    override fun getUserJob(
        onSuccess: (response: Job) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userModificationRemoteDataSource.getUserJob(onSuccess, onFailure)
    }

    override fun modifyUserJob(
        request: Job,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userModificationRemoteDataSource.modifyUserJob(request, onSuccess, onFailure)
    }

    override fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userModificationRemoteDataSource.modifyNickname(request, onSuccess, onFailure)
    }

    override fun modifyLocalNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        userModificationLocalDataSource.modifyNickname(request, onSuccess, onFailure)
    }

    override fun getTerms(
        onSuccess: (Terms) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userModificationRemoteDataSource.getTerms(onSuccess, onFailure)
    }
}