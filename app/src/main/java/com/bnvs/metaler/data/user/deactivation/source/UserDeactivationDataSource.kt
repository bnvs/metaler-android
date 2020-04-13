package com.bnvs.metaler.data.user.deactivation.source

import com.bnvs.metaler.data.user.certification.model.AddUserResponse
import com.bnvs.metaler.data.user.certification.model.CheckMembershipResponse

interface UserDeactivationDataSource {

    interface DeleteUserCallback {
        fun onUserDeleted(response: AddUserResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface LogoutCallback {
        fun onLogoutSuccess(response: CheckMembershipResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    fun deleteUser()

    fun logout()

}