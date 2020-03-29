package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.*

interface UserDataSource {

    interface AddUserCallback {
        fun onUserAdded(user: AddUserResponse)
        fun onFailure()
    }

    interface DeleteUserCallback {
        fun onUserDeleted(user: DeleteUserRequest)
        fun onFailure()
    }

    interface CheckMembershipCallback {
        fun onMembershipChecked(user: CheckMembershipResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface LoginCallback {
        fun onLoginSuccess(user: LoginResponse)
        fun onFailure()
    }

    fun addUser(callback: AddUserCallback)
    fun deleteUser(callback: DeleteUserCallback)

    fun checkMembership(checkMembershipRequest: CheckMembershipRequest, callback: CheckMembershipCallback)

    fun login(loginRequest: LoginRequest, callback: LoginCallback)
}