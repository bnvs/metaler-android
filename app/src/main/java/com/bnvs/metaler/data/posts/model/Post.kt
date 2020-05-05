package com.bnvs.metaler.data.posts.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

/**
 * Categories, Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물의 리스트를 구성하는 각각의 게시물 데이터를 정의하는 데이터 클래스
 * */

data class Post(
    val post_id: Int,
    val title: String,
    val content: String,
    val nickname: String,
    val date: String,
    val liked: Int,
    val disliked: Int,
    val thumbnail: String,
    val tags: List<PostTag>,
    var bookmark_id: Int
)