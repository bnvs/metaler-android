package com.bnvs.metaler.data.job.source

import android.content.Context
import com.bnvs.metaler.data.job.source.local.JobLocalDataSource
import com.bnvs.metaler.data.job.source.remote.JobRemoteDataSource

class JobRepository(context: Context) : JobDataSource{

    private val jobLocalDataSource = JobLocalDataSource(context)
    private val jobRemoteDataSource = JobRemoteDataSource

    override fun getJob(callback: JobDataSource.LoadJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}