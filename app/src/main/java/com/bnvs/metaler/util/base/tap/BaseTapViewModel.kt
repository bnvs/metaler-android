package com.bnvs.metaler.util.base.tap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.util.base.BaseViewModel

abstract class BaseTapViewModel : BaseViewModel() {
    // tapBarButtons
    private val _openHomeActivity = MutableLiveData<Boolean>().apply { value = false }
    val openHomeActivity: LiveData<Boolean> = _openHomeActivity
    private val _openMaterialsActivity = MutableLiveData<Boolean>().apply { value = false }
    val openMaterialsActivity: LiveData<Boolean> = _openMaterialsActivity
    private val _openManufacturesActivity = MutableLiveData<Boolean>().apply { value = false }
    val openManufacturesActivity: LiveData<Boolean> = _openManufacturesActivity
    private val _openBookmarksActivity = MutableLiveData<Boolean>().apply { value = false }
    val openBookmarksActivity: LiveData<Boolean> = _openBookmarksActivity
    private val _openMyPageActivity = MutableLiveData<Boolean>().apply { value = false }
    val openMyPageActivity: LiveData<Boolean> = _openMyPageActivity

    fun startHomeActivity() {
        _openHomeActivity.enable()
    }

    fun startMaterialsActivity() {
        _openMaterialsActivity.enable()
    }

    fun startManufacturesActivity() {
        _openManufacturesActivity.enable()
    }

    fun startBookmarksActivity() {
        _openBookmarksActivity.enable()
    }

    fun startMyPageActivity() {
        _openMyPageActivity.enable()
    }
}