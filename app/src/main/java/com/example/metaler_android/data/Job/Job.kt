package com.example.metaler_android.data.Job

/**
 * 사용자의 소속을 정의하는 data class
 * job -> 직업
 * job_type -> 직업 분류
 * job_detail -> 직업 상세
 * */

data class Job(
    val job: String,
    val job_type: String,
    val job_detail: String
)