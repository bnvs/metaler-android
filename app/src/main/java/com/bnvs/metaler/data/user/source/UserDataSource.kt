package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.*

interface UserDataSource {

    interface AddUserCallback {
        fun onUserAdded(response: AddUserResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface DeleteUserCallback {
        fun onUserDeleted(response: DeleteUserRequest)
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

    fun addUser(callback: AddUserCallback)
    fun deleteUser(callback: DeleteUserCallback)

    fun checkMembership(checkMembershipRequest: CheckMembershipRequest, callback: CheckMembershipCallback)

    fun login(loginRequest: LoginRequest, callback: LoginCallback)
}