package com.bnvs.metaler.data.user.certification.source

import com.bnvs.metaler.data.user.certification.model.*

interface UserCertificationDataSource {

    interface AddUserCallback {
        fun onUserAdded(response: AddUserResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface CheckMembershipCallback {
        fun onMembershipChecked(response: CheckMembershipResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface LoginCallback {
        fun onLoginSuccess(response: LoginResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    fun addUser(request: AddUserRequest, callback: AddUserCallback)

    fun checkMembership(request: CheckMembershipRequest, callback: CheckMembershipCallback)

    fun login(request: LoginRequest, callback: LoginCallback)
}