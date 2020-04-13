package com.bnvs.metaler.data.user.certification.model

data class LoginResponse(
    val access_token: String,
    val user: User,
    val code: String
)