package com.bnvs.metaler.data.user

data class LoginRequest(
    val kakao_id: String,
    val signin_token: String,
    val push_token: String,
    val device_id: String,
    val device_model: String,
    val device_os: String,
    val app_version: String
)