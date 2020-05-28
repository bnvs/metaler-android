package com.bnvs.metaler.data.user.modification.source.repository

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSource
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSource

class UserModificationRepositoryImpl(
    private val userModificationLocalDataSource: UserModificationLocalDataSource,
    private val userModificationRemoteDataSource: UserModificationRemoteDataSource
) : UserModificationRepository {

    override fun getUserJob(
        onSuccess: (response: Job) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userModificationRemoteDataSource.getUserJob(onSuccess, onFailure, handleError)
    }

    override fun modifyUserJob(
        request: Job,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userModificationRemoteDataSource.modifyUserJob(request, onSuccess, onFailure, handleError)
    }

    override fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userModificationRemoteDataSource.modifyNickname(request, onSuccess, onFailure, handleError)
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
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userModificationRemoteDataSource.getTerms(onSuccess, onFailure, handleError)
    }

    override fun saveTermsAgreements(
        request: TermsAgreements,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        userModificationLocalDataSource.saveTermsAgreements(request, onSuccess, onFailure)
    }

    override fun getTermsAgreements(
        onSuccess: (agreements: TermsAgreements) -> Unit,
        onFailure: () -> Unit
    ) {
        userModificationLocalDataSource.getTermsAgreements(onSuccess, onFailure)
    }
}