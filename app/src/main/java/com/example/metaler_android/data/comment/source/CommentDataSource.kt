package com.example.metaler_android.data.comment.source

import com.example.metaler_android.data.comment.Comments

interface CommentDataSource {

    interface LoadCommentsCallback {
        fun onCommentsLoaded(comments: Comments)
        fun onDataNotAvailable()
    }
    
    fun getComments(callback: LoadCommentsCallback)

}