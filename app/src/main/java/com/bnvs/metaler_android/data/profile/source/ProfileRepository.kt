package com.bnvs.metaler_android.data.profile.source

import android.content.Context
import com.bnvs.metaler_android.data.profile.source.local.ProfileLocalDataSource
import com.bnvs.metaler_android.data.profile.source.remote.ProfileRemoteDataSource

class ProfileRepository (context: Context) : ProfileDataSource {

    private val profileRemoteDataSource = ProfileRemoteDataSource
    private val profileLocalDataSource = ProfileLocalDataSource(context)

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}