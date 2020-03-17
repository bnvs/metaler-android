package com.example.metaler_android.data

/**
* Materials 와 Manufactures 탭에서
 * 게시물의 리스트를 보여주는데 사용하는
 * 데이터 클래스이다
 * is_next 는 다음 페이지의 유무
 * post_count 는 게시글 총 개수
* */

data class Posts(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<PostsItem>
)

// TODO : PostsItem 이라는 명명법이 적절한지 논의 필요
data class PostsItem(
    val post_id: Int,
    val title: String,
    val nickname: Int,
    val date: String,
    val tags: List<String>,
    val image_url: String,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean
)