package com.example.metaler_android.data

/**
* Materials 와 Manufactures 게시물의
 * 상세 내용을 조회하는데 사용하는
 * 데이터 클래스이다
* */

data class PostDetail(
    val Post: List<Post>
)

data class Post(
    val post_id: Int,
    val title: String,
    val nickname: Int,
    val date: String,
    val tags: List<String>,
    val image_url: String,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean,
    val content: String,
    val attachs: List<String>,
    // val tags: List<String>,
    val comment_count: Int,
    val price: Int,
    val user_rating: String
)