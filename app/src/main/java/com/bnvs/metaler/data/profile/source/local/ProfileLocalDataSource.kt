package com.bnvs.metaler.data.profile.source.local

import android.content.Context
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.ProfileDataSource
import org.json.JSONObject

class ProfileLocalDataSource (context: Context) : ProfileDataSource {

    private val sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getProfile(callback: ProfileDataSource.LoadProfileCallback) {
        var profileString = sharedPreferences.getString("profile", null)
        if (profileString != null) {
            var profile = JSONObject(profileString)
            callback.onProfileloaded(
                Profile(
                    profile.getString("profile_nickname"),
                    profile.getString("profile_image_url"),
                    profile.getString("profile_email")
                ))
        }else {
            callback.onProfileNotExist()
        }
    }

    override fun saveProfile(profile: Profile) {
        editor.putString("profile", profile.toString())
        editor.commit()
    }
}