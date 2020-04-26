package com.bnvs.metaler.data.homeposts.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

/**
 * Home 탭에서 보여질 게시물 리스트의
 * 각각의 게시물 데이터를 정의하는 데이터 클래스
 * 재료/가공 상관없이 다 HomePost 사용
 * */

data class HomePost(
    val post_id: Int,
    val title: String,
    val content: String,
    val profile_nickname: String,
    val date: String,
    val tags: List<PostTag>
)