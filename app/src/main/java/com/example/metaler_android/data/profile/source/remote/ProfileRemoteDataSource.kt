package com.example.metaler_android.data.profile.source.remote

import com.example.metaler_android.data.profile.source.ProfileDataSource
import com.example.metaler_android.network.RetrofitClient

object ProfileRemoteDataSource : ProfileDataSource {
    private val retrofitClient = RetrofitClient.client

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}