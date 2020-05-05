package com.bnvs.metaler.ui.modifycomment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R

class ActivityModifyComment : AppCompatActivity(), ContractModifyComment.View {

    override lateinit var presenter: ContractModifyComment.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_comment)
    }
}
