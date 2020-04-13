package com.bnvs.metaler.data.user.certification.source

import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.CheckMembershipRequest
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSource

class UserCertificationRepository : UserCertificationDataSource {

    private val userRemoteDataSource =
        UserCertificationRemoteDataSource

    override fun addUser(
        request: AddUserRequest,
        callback: UserCertificationDataSource.AddUserCallback
    ) {
        userRemoteDataSource.addUser(request, callback)
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        callback: UserCertificationDataSource.CheckMembershipCallback
    ) {
        userRemoteDataSource.checkMembership(request, callback)
    }

    override fun login(
        request: LoginRequest,
        callback: UserCertificationDataSource.LoginCallback
    ) {
        userRemoteDataSource.login(request, callback)
    }
}