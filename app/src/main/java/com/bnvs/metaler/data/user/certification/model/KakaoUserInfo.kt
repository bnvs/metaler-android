package com.bnvs.metaler.data.user.certification.model

data class KakaoUserInfo(
    val kakao_id: String,
    val profile_nickname: String,
    val profile_image_url: String,
    val profile_email: String?,
    val profile_gender: String?
)