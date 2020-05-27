package com.bnvs.metaler.data.user.certification.source.repository

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.local.UserCertificationLocalDataSource
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSource

class UserCertificationRepositoryImpl(
    private val userCertificationLocalDataSource: UserCertificationLocalDataSource,
    private val userCertificationRemoteDataSource: UserCertificationRemoteDataSource
) : UserCertificationRepository {

    override fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userCertificationRemoteDataSource.addUser(request, onSuccess, onFailure, handleError)
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userCertificationRemoteDataSource.checkMembership(
            request,
            onSuccess,
            onFailure,
            handleError
        )
    }

    override fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        userCertificationRemoteDataSource.login(request, onSuccess, onFailure, handleError)
    }

    override fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    ) {
        userCertificationLocalDataSource.getKakaoUserInfo(onSuccess, onFailure)
    }

    override fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo) {
        userCertificationLocalDataSource.saveKakaoUserInfo(kakaoUserInfo)
    }

    override fun deleteKakaoUserInfo() {
        userCertificationLocalDataSource.deleteKakaoUserInfo()
    }
}