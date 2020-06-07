package com.bnvs.metaler.data.user.certification.source.local

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.bnvs.metaler.util.DeviceInfo
import com.bnvs.metaler.util.constants.KAKAO_USER_INFO
import com.bnvs.metaler.util.constants.LOCAL_KAKAO_USER_INFO_DATA
import com.google.gson.GsonBuilder

class UserCertificationLocalDataSourceImpl(context: Context) : UserCertificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences(LOCAL_KAKAO_USER_INFO_DATA, Context.MODE_PRIVATE)

    private val deviceInfo = DeviceInfo(context)

    override fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    ) {
        val kakaoUserInfo = sharedPreferences.getString(KAKAO_USER_INFO, null)
        if (kakaoUserInfo != null) {
            onSuccess(GsonBuilder().create().fromJson(kakaoUserInfo, KakaoUserInfo::class.java))
        } else {
            onFailure()
        }
    }

    override fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo) {
        Log.d("saveKakaoUserInfo", GsonBuilder().create().toJson(kakaoUserInfo))
        sharedPreferences.edit()
            .putString(KAKAO_USER_INFO, GsonBuilder().create().toJson(kakaoUserInfo)).commit()
    }

    override fun deleteKakaoUserInfo() {
        sharedPreferences.edit().clear().commit()
    }

    override fun getDeviceInfo(): DeviceInfo {
        return deviceInfo
    }
}