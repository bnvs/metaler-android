package com.bnvs.metaler.data.job.source.local

import android.content.Context
import com.bnvs.metaler.data.job.source.JobDataSource

class JobLocalDataSource(context: Context) : JobDataSource {

    private val sharedPreferences = context.getSharedPreferences("job", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getJob(callback: JobDataSource.LoadJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyJob(callback: JobDataSource.ModifyJobCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}