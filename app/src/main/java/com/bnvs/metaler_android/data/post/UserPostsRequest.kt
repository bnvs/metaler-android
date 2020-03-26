package com.bnvs.metaler_android.data.post

import org.json.JSONObject

data class UserPostsRequest(
    val access_token: String,
    val posts: List<JSONObject>
)