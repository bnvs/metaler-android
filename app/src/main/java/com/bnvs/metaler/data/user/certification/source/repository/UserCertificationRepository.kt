package com.bnvs.metaler.data.user.certification.source.repository

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.util.DeviceInfo

interface UserCertificationRepository {

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

    fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    )

    fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo)

    fun deleteKakaoUserInfo()

    fun getDeviceInfo(): DeviceInfo
}