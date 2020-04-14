package com.bnvs.metaler.data.profile.source

import android.content.Context
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSource

class ProfileRepository(context: Context) : ProfileDataSource {

    private val profileLocalDataSource = ProfileLocalDataSource(context)

    override fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    ) {
        profileLocalDataSource.getProfile(onProfileLoaded, onProfileNotExist)
    }

    override fun saveProfile(profile: Profile) {
        profileLocalDataSource.saveProfile(profile)
    }
}