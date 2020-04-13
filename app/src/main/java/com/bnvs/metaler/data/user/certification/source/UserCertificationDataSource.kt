package com.bnvs.metaler.data.user.certification.source

import com.bnvs.metaler.data.user.certification.model.*
import retrofit2.HttpException

interface UserCertificationDataSource {

    interface AddUserCallback {
        fun onUserAdded(response: AddUserResponse)
        fun onResponseError(exception: HttpException)
        fun onFailure(t: Throwable)
    }

    interface CheckMembershipCallback {
        fun onMembershipChecked(response: CheckMembershipResponse)
        fun onResponseError(exception: HttpException)
        fun onFailure(t: Throwable)
    }

    interface LoginCallback {
        fun onLoginSuccess(response: LoginResponse)
        fun onResponseError(exception: HttpException)
        fun onFailure(t: Throwable)
    }

    fun addUser(request: AddUserRequest, callback: AddUserCallback)

    fun checkMembership(request: CheckMembershipRequest, callback: CheckMembershipCallback)

    fun login(request: LoginRequest, callback: LoginCallback)
}