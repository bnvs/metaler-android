package com.bnvs.metaler.data.user.source

import org.json.JSONObject

interface UserDataSource {

    interface AddUserCallback {
        fun onUserAdded(user: JSONObject)
        fun onFailure()
    }

    fun addUser(callback: AddUserCallback)

}