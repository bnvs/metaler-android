package com.example.metaler_android.data.Post.source

import com.example.metaler_android.data.Comment.Comments
import com.example.metaler_android.data.HomePost.HomePosts
import com.example.metaler_android.data.Job.Job
import com.example.metaler_android.data.Material.Materials
import com.example.metaler_android.data.Post.Posts
import com.example.metaler_android.data.PostDetail.PostDetails
import org.json.JSONObject

interface PostDataSource {
    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }

    interface LoadMaterialsCallback {
        fun onMaterialsLoaded(materials: Materials)
        fun onDataNotAvailable()
    }

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }

    interface LoadCommentsCallback {
        fun onCommentsLoaded(comments: Comments)
        fun onDataNotAvailable()
    }

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }

    interface AddUserCallback {
        fun onUserAdded(user: JSONObject)
        fun onFailure()
    }

    interface AddPostCallback {
        fun onPostAdded()
        fun onFailure()
    }

    interface UploadFileCallback {
        fun onFileUploaded()
        fun onFailure()
    }

    interface AddBookmarkCallback {
        fun onBookmarkAdded()
        fun onFailure()
    }

    fun getHomePosts(callback: LoadHomePostsCallback)
    
    fun getMaterials(callback: LoadMaterialsCallback)

    fun getPosts(callback: LoadPostsCallback)

    fun getPostDetails(callback: LoadPostDetailsCallback)

    fun getComments(callback: LoadCommentsCallback)

    fun getJob(callback: LoadJobCallback)

    fun addUser(callback: AddUserCallback)

    fun addPost(callback: AddPostCallback)

    fun uploadFile(callback: UploadFileCallback)

    fun addBookmark(callback: AddBookmarkCallback)
}