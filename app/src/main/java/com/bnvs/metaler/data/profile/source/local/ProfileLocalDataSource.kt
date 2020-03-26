package com.bnvs.metaler.data.profile.source.local

import android.content.Context
import com.bnvs.metaler.data.profile.source.ProfileDataSource

class ProfileLocalDataSource (context: Context) : ProfileDataSource {

    private val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}