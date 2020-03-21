package com.example.metaler_android.data.comments.source

import com.example.metaler_android.data.comments.Comments

interface CommentsDataSource {
   
    interface LoadCommentsCallback {
        fun onCommentsLoaded(comments: Comments)
        fun onDataNotAvailable()
    }
    
    fun getComments(callback: LoadCommentsCallback)
    
}