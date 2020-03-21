package com.example.metaler_android.data.homepost

/**
 * Home 탭에서 보여질 게시물 리스트의
 * 각각의 게시물 데이터를 정의하는 데이터 클래스
 * 재료/가공 상관없이 다 HomePost 사용
 * */

data class HomePost(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val tags: List<String>
)