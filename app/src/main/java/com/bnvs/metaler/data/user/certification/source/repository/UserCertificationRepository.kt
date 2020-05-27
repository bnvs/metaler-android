package com.bnvs.metaler.data.user.certification.source.repository

import com.bnvs.metaler.data.user.certification.model.*

interface UserCertificationRepository {

    fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}