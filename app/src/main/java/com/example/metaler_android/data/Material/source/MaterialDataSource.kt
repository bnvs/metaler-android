package com.example.metaler_android.data.Material.source

import com.example.metaler_android.data.Comment.Comments
import com.example.metaler_android.data.HomePost.HomePosts
import com.example.metaler_android.data.Job.Job
import com.example.metaler_android.data.Material.Materials
import com.example.metaler_android.data.Post.Posts
import com.example.metaler_android.data.PostDetail.PostDetails
import org.json.JSONObject

interface MaterialDataSource {

    interface LoadMaterialsCallback {
        fun onMaterialsLoaded(materials: Materials)
        fun onDataNotAvailable()
    }

    fun getMaterials(callback: LoadMaterialsCallback)

}