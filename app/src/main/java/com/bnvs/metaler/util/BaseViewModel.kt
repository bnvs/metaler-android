package com.bnvs.metaler.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE

abstract class BaseViewModel : ViewModel() {

    // errorMessage Handling
    protected val _errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorToastMessage: LiveData<String> = _errorToastMessage
    protected val _errorDialogMessage = MutableLiveData<String>().apply { value = "" }
    val errorDialogMessage: LiveData<String> = _errorDialogMessage
    protected val _errorCode = MutableLiveData<Int>().apply { value = NO_ERROR_TO_HANDLE }
    val errorCode: LiveData<Int> = _errorCode

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
        _openHomeActivity.apply {
            value = true
            value = false
        }
    }

    fun startMaterialsActivity() {
        _openMaterialsActivity.apply {
            value = true
            value = false
        }
    }

    fun startManufacturesActivity() {
        _openManufacturesActivity.apply {
            value = true
            value = false
        }
    }

    fun startBookmarksActivity() {
        _openBookmarksActivity.apply {
            value = true
            value = false
        }
    }

    fun startMyPageActivity() {
        _openMyPageActivity.apply {
            value = true
            value = false
        }
    }

    protected fun clearStringValue(): String {
        return ""
    }
}