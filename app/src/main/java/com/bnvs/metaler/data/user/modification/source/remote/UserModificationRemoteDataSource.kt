package com.bnvs.metaler.data.user.modification.source.remote

import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.UserModificationDataSource

object UserModificationRemoteDataSource : UserModificationDataSource {
    override fun getUserJob(
        access_token: String,
        callback: UserModificationDataSource.GetUserJobCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyUserJob(
        access_token: String,
        request: Job,
        callback: UserModificationDataSource.ModifyUserJobCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyNickname(access_token: String, request: Nickname) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}