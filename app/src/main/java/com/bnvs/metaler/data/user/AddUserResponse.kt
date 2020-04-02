package com.bnvs.metaler.data.user

data class AddUserResponse(
    val user_id: Int,
    val signin_token: String,
    val code: String
)