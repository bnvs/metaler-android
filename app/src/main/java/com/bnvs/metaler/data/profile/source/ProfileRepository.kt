package com.bnvs.metaler.data.profile.source

import android.content.Context
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSource
import com.bnvs.metaler.data.profile.source.remote.ProfileRemoteDataSource

class ProfileRepository (context: Context) : ProfileDataSource {

    private val profileRemoteDataSource = ProfileRemoteDataSource
    private val profileLocalDataSource = ProfileLocalDataSource(context)

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        profileLocalDataSource.getProfile(callback)
    }

    override fun saveProfile(profile: Profile) {
        profileLocalDataSource.saveProfile(profile)
    }
}