package com.bnvs.metaler.data.profile.source

import com.bnvs.metaler.data.profile.Profile

interface ProfileDataSource {

    interface LoadProfileCallback {
        fun onProfileloaded(profile: Profile)
        fun onProfileNotExist()
    }

    fun getProfile(callback: LoadProfileCallback)

    fun saveProfile(profile: Profile)

}