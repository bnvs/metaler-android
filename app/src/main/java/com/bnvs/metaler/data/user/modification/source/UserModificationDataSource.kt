package com.bnvs.metaler.data.user.modification.source

import com.bnvs.metaler.data.user.certification.model.AddUserResponse
import com.bnvs.metaler.data.user.certification.model.CheckMembershipResponse
import com.bnvs.metaler.data.user.certification.model.LoginResponse
import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname

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

    fun getUserJob(access_token: String, callback: GetUserJobCallback)

    fun modifyUserJob(access_token: String, request: Job, callback: ModifyUserJobCallback)

    fun modifyNickname(access_token: String, request: Nickname)

}