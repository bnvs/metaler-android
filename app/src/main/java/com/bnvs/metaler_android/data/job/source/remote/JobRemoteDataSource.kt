package com.bnvs.metaler_android.data.job.source.remote

import com.bnvs.metaler_android.data.job.source.JobDataSource
import com.bnvs.metaler_android.network.RetrofitClient

object JobRemoteDataSource : JobDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getJob(callback: JobDataSource.LoadJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}