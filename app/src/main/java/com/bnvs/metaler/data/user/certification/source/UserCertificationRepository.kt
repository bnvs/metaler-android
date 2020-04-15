package com.bnvs.metaler.data.user.certification.source

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSource

class UserCertificationRepository : UserCertificationDataSource {

    private val userRemoteDataSource =
        UserCertificationRemoteDataSource

    override fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.addUser(request, onSuccess, onFailure)
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.checkMembership(request, onSuccess, onFailure)
    }

    override fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userRemoteDataSource.login(request, onSuccess, onFailure)
    }
}