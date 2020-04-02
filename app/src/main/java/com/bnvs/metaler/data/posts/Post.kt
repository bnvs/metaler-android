package com.bnvs.metaler.data.posts

/**
 * Categories, Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물의 리스트를 구성하는 각각의 게시물 데이터를 정의하는 데이터 클래스
 * */

data class Post(
    val id: Int,
    val user_id: Int,
    val category_id: Int,
    val title: String,
    val content: String,
    val price: Int,
    val price_type: String,
    val created_at: String,
    val updated_at: String,
    val profile_nickname: String,
    val tags: List<String>,
    val good_count: Int,
    val hate_count: Int,
    var is_bookmark: Boolean
)