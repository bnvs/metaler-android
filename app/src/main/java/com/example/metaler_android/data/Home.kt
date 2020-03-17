package com.example.metaler_android.data

/**
* Home 액티비티에서
 * 재료와 가공의 최신 게시물을 보여주는 데 사용하는
 * 데이터 클래스
* */

data class Home(
    val materials: List<HomePost>,
    val manufactures: List<HomePost>
)

// TODO : HomePost 라는 명명법이 적절한지 논의 필요
data class HomePost(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val tags: List<String>
)

