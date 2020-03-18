package com.example.metaler_android.data.User.source

import org.json.JSONObject

interface UserDataSource {

    interface AddUserCallback {
        fun onUserAdded(user: JSONObject)
        fun onFailure()
    }

    fun addUser(callback: AddUserCallback)

}