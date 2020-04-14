package com.bnvs.metaler.data.postdetails.model

/**
 * Categories 와 Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물을 클릭하여 게시물 상세 내용을 조회할 때 사용하는 데이터 클래스
 * */

// TODO : 스프레드 시트에 작성된 내용에 의하면, PostDetail JSON 객체를 배열로 감싸서 반환하던데 그 이유가 무엇인지 질문
data class PostDetails(
    val Post: List<PostDetail>
)