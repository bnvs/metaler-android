package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Jobs
import com.bnvs.metaler.data.user.modification.model.Nickname
import okhttp3.ResponseBody

interface UserModificationDataSource {

    fun getUserJob(
        onSuccess: (response: Jobs) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyUserJob(
        request: Job,
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyNickname(
        request: Nickname,
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}