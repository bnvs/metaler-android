package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.Terms

interface UserModificationDataSource {

    fun getUserJob(
        onSuccess: (response: Job) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyUserJob(
        request: Job,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getTerms(
        onSuccess: (Terms) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}