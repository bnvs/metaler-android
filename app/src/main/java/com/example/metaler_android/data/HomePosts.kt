package com.example.metaler_android.data

/**
 * Home 탭에서
 * 재료 최신 게시물 -> materials: List<HomePost>
 * 가공 최신 게시물 -> manufactures: List<HomePost>
 * 보여주는 데 사용하는 데이터 클래스
 * 
 * HomePost 는 각각의 게시물 데이터를 정의하는 데이터 클래스
* */

data class HomePosts(
    val materials: List<HomePost>,
    val manufactures: List<HomePost>
)

data class HomePost(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val tags: List<String>
)

