package com.bnvs.metaler.data.bookmarks.source

interface BookmarksDataSource{
    interface DeleteBookmarkCallback {
        fun onBookmarkDeleted()
        fun onDataNotAvailable()
    }
    fun deleteBookmark(callback: DeleteBookmarkCallback)
}