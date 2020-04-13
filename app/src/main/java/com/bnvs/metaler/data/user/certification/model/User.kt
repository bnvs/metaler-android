package com.bnvs.metaler.data.user.certification.model

data class User(
    val id: Int,
    val profile_nickname: String,
    val profile_image_url: String,
    val profile_email: String,
    val profile_birthday: String,
    val profile_gender: String,
    val job: String,
    val job_type: String,
    val job_detail: String,
    val push_allowed: Int,
    val push_token: String,
    val signin_token: String,
    val last_login_at: String,
    val device_id: String,
    val device_model: String,
    val device_os: String,
    val app_version: String
)