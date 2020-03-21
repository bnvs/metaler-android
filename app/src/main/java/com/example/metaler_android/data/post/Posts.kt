package com.example.metaler_android.data.post

/**
 * Categories, Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물의 리스트를 보여주는데 사용하는 데이터 클래스
 * is_next -> 다음 페이지의 유무
 * post_count -> 게시글 총 개수
 * posts -> 게시물 리스트 (서버에 요청한 개수의 게시물들이 들어있음)
 * */

data class Posts(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<Post>
)