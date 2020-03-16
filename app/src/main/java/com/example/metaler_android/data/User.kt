package com.example.metaler_android.data

data class User(
    val profile_nickname: String,
    val profile_image_url: String,
    val profile_email: String,
    val profile_birthday: String,
    val profile_gender: String,
    val job: String,
    val job_type: String,
    val job_detail: String,
    val service_type: String,
    val push_token: String,
    val device_model: String,
    val device_id: String,
    val device_os: String,
    val app_version: String
)