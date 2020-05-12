package com.bnvs.metaler.util

import android.view.View

/**
 * 재료/가공 탭의 게시물 리사이클러뷰에 달아줄
 * 아이템 클릭 리스너 인터페이스입니다.
 * onPostClick -> 게시물을 클릭했을때
 * onBookmarkButtonClick -> 북마크 버튼을 클릭했을때
 * */

interface PostItemListener {
    fun onPostClick(view: View, clickedPostId: Int)

    fun onBookmarkButtonClick(view: View, clickedPostId: Int, bookmarkId: Int, position: Int)
}