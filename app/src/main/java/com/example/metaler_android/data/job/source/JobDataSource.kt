package com.example.metaler_android.data.job.source

import com.example.metaler_android.data.job.Job
import org.json.JSONObject

interface JobDataSource {

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }

    fun getJob(callback: LoadJobCallback)
    
}