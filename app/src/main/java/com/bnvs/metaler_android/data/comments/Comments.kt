package com.bnvs.metaler_android.data.comments

/**
 * Categories 와 Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물 상세 댓글 조회시에 사용하는 데이터 클래스
 * 게시물 상세내용과 게시물 댓글은 따로 조회된다
 * */

data class Comments(
    val comments:List<Comment>
)