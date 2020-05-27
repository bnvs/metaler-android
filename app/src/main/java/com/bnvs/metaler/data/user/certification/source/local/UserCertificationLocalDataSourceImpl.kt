package com.bnvs.metaler.data.user.certification.source.local

import android.content.Context
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.google.gson.GsonBuilder

class UserCertificationLocalDataSourceImpl(context: Context) : UserCertificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("KAKAO_USER_INFO", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getKakaoUserInfo(
        onSuccess: (kakaoUserInfo: KakaoUserInfo) -> Unit,
        onFailure: () -> Unit
    ) {
        val kakaoUserInfo = sharedPreferences.getString("kakaoUserInfo", null)
        if (kakaoUserInfo != null) {
            onSuccess(GsonBuilder().create().fromJson(kakaoUserInfo, KakaoUserInfo::class.java))
        } else {
            onFailure()
        }
    }

    override fun saveKakaoUserInfo(kakaoUserInfo: KakaoUserInfo) {
        editor.putString("kakaoUserInfo", GsonBuilder().create().toJson(kakaoUserInfo))
        editor.commit()
    }

    override fun deleteKakaoUserInfo() {
        editor.clear().commit()
    }
}