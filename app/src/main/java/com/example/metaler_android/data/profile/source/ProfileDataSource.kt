package com.example.metaler_android.data.profile.source

import org.json.JSONObject

interface ProfileDataSource {

    interface LoadProfileCallback {
        fun onProfileloaded(user: JSONObject)
        fun onFailure()
    }

    fun getProfile(callback: LoadProfileCallback)

}