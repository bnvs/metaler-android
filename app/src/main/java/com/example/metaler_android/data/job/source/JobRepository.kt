package com.example.metaler_android.data.job.source

import android.content.Context
import com.example.metaler_android.data.job.source.local.JobLocalDataSource
import com.example.metaler_android.data.job.source.remote.JobRemoteDataSource

class JobRepository(context: Context) : JobDataSource{

    private val jobLocalDataSource = JobLocalDataSource(context)
    private val jobRemoteDataSource = JobRemoteDataSource

    override fun getJob(callback: JobDataSource.LoadJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyJob(callback: JobDataSource.ModifyJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}