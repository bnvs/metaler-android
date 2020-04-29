package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Jobs
import com.bnvs.metaler.data.user.modification.model.Nickname

interface UserModificationDataSource {

    fun getUserJob(
        onSuccess: (response: Jobs) -> Unit,
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

}