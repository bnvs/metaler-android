package com.bnvs.metaler.data.user.certification.source.local

import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.bnvs.metaler.util.DeviceInfo

interface UserCertificationLocalDataSource {

    fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    )

    fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo)

    fun deleteKakaoUserInfo()

    fun getDeviceInfo(): DeviceInfo
}