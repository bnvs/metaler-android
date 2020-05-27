package com.bnvs.metaler.data.user.modification.source.repository

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms

interface UserModificationRepository {

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

    fun modifyLocalNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun getTerms(
        onSuccess: (Terms) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}