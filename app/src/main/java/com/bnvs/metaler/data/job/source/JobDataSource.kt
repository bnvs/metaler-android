package com.bnvs.metaler.data.job.source

import com.bnvs.metaler.data.job.Job

interface JobDataSource {

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }

    interface ModifyJobCallback {
        fun onJobModified()
        fun onDataNotAvailable()
    }

    fun getJob(callback: LoadJobCallback)

    fun modifyJob(callback: ModifyJobCallback)

}