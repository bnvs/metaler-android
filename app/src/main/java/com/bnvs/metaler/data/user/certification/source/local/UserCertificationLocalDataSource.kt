package com.bnvs.metaler.data.user.certification.source.local

import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo

interface UserCertificationLocalDataSource {

    fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    )

    fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo)

    fun deleteKakaoUserInfo()
}