package com.bnvs.metaler.data.user.certification.source.remote

import com.bnvs.metaler.data.user.certification.model.*

interface UserCertificationRemoteDataSource {

    fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}