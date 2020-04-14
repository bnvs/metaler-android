package com.bnvs.metaler.data.profile.source

import com.bnvs.metaler.data.profile.model.Profile

interface ProfileDataSource {

    fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    )

    fun saveProfile(profile: Profile)

}