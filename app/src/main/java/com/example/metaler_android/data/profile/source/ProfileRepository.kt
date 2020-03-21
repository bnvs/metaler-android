package com.example.metaler_android.data.profile.source

import com.example.metaler_android.data.profile.source.local.ProfileLocalDataSource
import com.example.metaler_android.data.profile.source.remote.ProfileRemoteDataSource

class ProfileRepository(
    val profileRemoteDataSource: ProfileRemoteDataSource,
    val profileLocalDataSource: ProfileLocalDataSource
) : ProfileDataSource {

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}