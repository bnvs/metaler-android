package com.bnvs.metaler_android.data.job.source

import com.bnvs.metaler_android.data.job.Job

interface JobDataSource {

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }

    fun getJob(callback: LoadJobCallback)

}