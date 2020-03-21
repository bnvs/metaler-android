package com.example.metaler_android.data.profile.source

import com.example.metaler_android.data.profile.source.local.ProfileLocalDataSource
import com.example.metaler_android.data.profile.source.remote.ProfileRemoteDataSource

object ProfileRepository : ProfileDataSource {

    val profileRemoteDataSource = ProfileRemoteDataSource()
    val profileLocalDataSource = ProfileLocalDataSource()

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}