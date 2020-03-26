package com.bnvs.metaler_android.data.user

import org.json.JSONObject

data class DeleteUserRequest(
    val access_token: String,
    val user: List<JSONObject>
)