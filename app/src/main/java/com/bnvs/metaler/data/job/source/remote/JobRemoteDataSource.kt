package com.bnvs.metaler.data.job.source.remote

import com.bnvs.metaler.data.job.source.JobDataSource
import com.bnvs.metaler.network.RetrofitClient

object JobRemoteDataSource : JobDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getJob(callback: JobDataSource.LoadJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyJob(callback: JobDataSource.ModifyJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}