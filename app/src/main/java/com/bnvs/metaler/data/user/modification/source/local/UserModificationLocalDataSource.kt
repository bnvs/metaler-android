package com.bnvs.metaler.data.user.modification.source.local

import com.bnvs.metaler.data.user.modification.model.Nickname

interface UserModificationLocalDataSource {

    fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )
}