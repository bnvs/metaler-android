package com.bnvs.metaler.ui.postfirst

import android.content.Intent
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository

class PresenterPostFirst(
    private val view: ContractPostFirst.View
) : ContractPostFirst.Presenter {

    private val addEditPostRepository = AddEditPostRepository()

    override fun start() {

    }

    override fun getCategoryId(intent: Intent) {
        
    }

    override fun getImageFromAlbum() {

    }

    override fun getImageFromCamera() {

    }

    override fun uploadImage() {

    }

    override fun getAttachUrl() {

    }

    override fun openPostSecond() {

    }
}