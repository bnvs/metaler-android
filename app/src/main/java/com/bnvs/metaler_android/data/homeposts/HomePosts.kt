package com.bnvs.metaler_android.data.homeposts

/**
 * Home 탭에서
 * 재료 최신 게시물 -> materials: List<HomePost>
 * 가공 최신 게시물 -> manufactures: List<HomePost>
 * 보여주는 데 사용하는 데이터 클래스
 * */

data class HomePosts(
    val materials: List<HomePost>,
    val manufactures: List<HomePost>
)

