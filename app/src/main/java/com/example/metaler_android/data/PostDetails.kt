package com.example.metaler_android.data

/**
 * Materials 와 Manufactures 탭에서
 * 게시물을 클릭하여
 * 게시물 상세 내용을 조회할 때 사용하는
 * 데이터 클래스
* */

// TODO : 스프레드 시트에 작성된 내용에 의하면, PostDetail JSON 객체를 배열로 감싸서 반환하던데 그 이유가 무엇인지 질문
data class PostDetails(
    val Post: List<PostDetail>
)

// TODO : tags 가 두 개 들어있는데 둘 의 차이가 무엇인지?
data class PostDetail(
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