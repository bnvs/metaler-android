package com.bnvs.metaler.data.profile.source

import com.bnvs.metaler.data.profile.Profile

interface ProfileDataSource {

    interface LoadProfileCallback {
        fun onProfileloaded(profile: Profile)
        fun onFailure()
    }

    fun getProfile(callback: LoadProfileCallback)

}