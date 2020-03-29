package com.bnvs.metaler.data.user

/**
 * 카카오 로그인을 했을때 반환되는 정보 + 추가 정보(유저 입력)
 * metaler 자체 로그인/회원가입 api 에 전달하는 정보
 * */

data class AddUserRequest(
    val kakao_id: String,
    val profile_nickname: String,
    val profile_image_url: String,
    val profile_email: String,
    val profile_birthday: String,
    val profile_gender: String,
    val job: String,
    val job_type: String,
    val job_detail: String
)