package com.bnvs.metaler.data.job.source

import com.bnvs.metaler.data.job.Job

interface JobDataSource {

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }

    fun getJob(callback: LoadJobCallback)

}