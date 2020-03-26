package com.bnvs.metaler.data.comments.source

import com.bnvs.metaler.data.comments.Comments

interface CommentsDataSource {
   
    interface LoadCommentsCallback {
        fun onCommentsLoaded(comments: Comments)
        fun onDataNotAvailable()
    }
    
    fun getComments(callback: LoadCommentsCallback)
    
}