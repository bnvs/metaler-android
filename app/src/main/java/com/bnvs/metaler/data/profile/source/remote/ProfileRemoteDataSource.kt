package com.bnvs.metaler.data.profile.source.remote

import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.ProfileDataSource
import com.bnvs.metaler.network.RetrofitClient

object ProfileRemoteDataSource : ProfileDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        // 서버에서 프로필 가져오기
    }

    override fun saveProfile(profile: Profile) {
        // 서버에 프로필 저장
    }
}