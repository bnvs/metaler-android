package com.example.metaler_android.data

/**
* Materials 와 Manufactures 게시물의
 * 상세 내용을 조회하는데 사용하는
 * 데이터 클래스이다
* */

// TODO : PostDetail 과 Post 명명법이 적절한지 논의 필요

// TODO : 스프레드 시트에 작성된 내용에 의하면, Post JSON 객체를 배열로 감싸서 반환하던데 그 이유가 무엇인지 질문
data class PostDetail(
    val Post: List<Post>
)

// TODO : tags 가 두 개 들어있는데 둘 의 차이가 무엇인지?
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