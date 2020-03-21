package com.example.metaler_android.data.profile.source

import com.example.metaler_android.data.profile.Profile

interface ProfileDataSource {

    interface LoadProfileCallback {
        fun onProfileloaded(profile: Profile)
        fun onFailure()
    }

    fun getProfile(callback: LoadProfileCallback)

}