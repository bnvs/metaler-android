package com.bnvs.metaler.data.user.certification.model

data class LoginRequest(
    val unique_id: String,
    val regi_type: String,
    val signin_token: String,
    val push_token: String,
    val device_id: String,
    val device_model: String,
    val device_os: String,
    val app_version: String
)