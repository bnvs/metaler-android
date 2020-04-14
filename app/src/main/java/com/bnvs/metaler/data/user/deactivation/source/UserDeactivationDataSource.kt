package com.bnvs.metaler.data.user.deactivation.source

import okhttp3.ResponseBody

interface UserDeactivationDataSource {

    fun deleteUser(
        onSuccess: (response: ResponseBody) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun logout()

}