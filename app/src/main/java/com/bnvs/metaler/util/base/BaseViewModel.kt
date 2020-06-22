package com.bnvs.metaler.util.base

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

    protected fun MutableLiveData<Boolean>.enable() {
        this.apply {
            value = true
            value = false
        }
    }

    protected fun MutableLiveData<String>.setMessage(message: String) {
        this.apply {
            value = message
            value = clearStringValue()
        }
    }

    protected fun MutableLiveData<Int>.setErrorCode(errorCode: Int) {
        this.apply {
            value = errorCode
            value = NO_ERROR_TO_HANDLE
        }
    }

    protected fun clearStringValue(): String {
        return ""
    }
}