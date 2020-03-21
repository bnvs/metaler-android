package com.example.metaler_android.data.profile.source.local

import android.content.Context
import com.example.metaler_android.data.profile.source.ProfileDataSource

class ProfileLocalDataSource (context: Context) : ProfileDataSource {

    private val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}