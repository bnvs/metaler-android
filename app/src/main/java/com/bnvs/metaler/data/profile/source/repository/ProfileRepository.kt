package com.bnvs.metaler.data.profile.source.repository

import android.content.Context
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.ProfileDataSource
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