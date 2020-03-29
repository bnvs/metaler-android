package com.bnvs.metaler.data.user.source

import com.bnvs.metaler.data.user.AddUserResponse
import com.bnvs.metaler.data.user.CheckMembershipResponse
import com.bnvs.metaler.data.user.DeleteUserRequest
import com.bnvs.metaler.data.user.LoginResponse

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

    fun checkMembership(kakao_id: String, callback: CheckMembershipCallback)

    fun login(callback: LoginCallback)
}