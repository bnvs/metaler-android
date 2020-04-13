package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.certification.model.AddUserResponse
import com.bnvs.metaler.data.user.certification.model.CheckMembershipResponse
import com.bnvs.metaler.data.user.certification.model.LoginResponse

interface UserModificationDataSource {

    interface GetUserJobCallback {
        fun onUserJobLoaded(response: AddUserResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface ModifyUserJobCallback {
        fun onUserJobModified(response: CheckMembershipResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    interface ModifyNicknameCallback {
        fun onNicknameModified(response: LoginResponse)
        fun onResponseError(message: String)
        fun onFailure(t: Throwable)
    }

    fun getUserJob()

    fun modifyUserJob()

    fun modifyNickname()

}