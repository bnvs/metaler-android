package com.bnvs.metaler.data.user.certification.source.local

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.google.gson.GsonBuilder

class UserCertificationLocalDataSourceImpl(context: Context) : UserCertificationLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("KAKAO_USER_INFO", Context.MODE_PRIVATE)

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
        Log.d("saveKakaoUserInfo", GsonBuilder().create().toJson(kakaoUserInfo))
        sharedPreferences.edit()
            .putString("kakaoUserInfo", GsonBuilder().create().toJson(kakaoUserInfo)).commit()
    }

    override fun deleteKakaoUserInfo() {
        sharedPreferences.edit().clear().commit()
    }
}