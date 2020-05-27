package com.bnvs.metaler.data.user.modification.source.remote

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms

interface UserModificationRemoteDataSource {

    fun getUserJob(
        onSuccess: (response: Job) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun modifyUserJob(
        request: Job,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun getTerms(
        onSuccess: (Terms) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}