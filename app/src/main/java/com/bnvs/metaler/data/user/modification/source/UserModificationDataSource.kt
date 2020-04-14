package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.modification.model.*

interface UserModificationDataSource {

    fun getUserJob(
        onSuccess: (response: Jobs) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyUserJob(
        request: Job,
        onSuccess: (response: ModifyJobResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun modifyNickname(
        request: Nickname,
        onSuccess: (response: ModifyNicknameResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}