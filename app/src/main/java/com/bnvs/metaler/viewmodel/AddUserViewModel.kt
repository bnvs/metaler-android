package com.bnvs.metaler.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.R
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepositoryImpl
import com.bnvs.metaler.network.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.network.NetworkUtil

class AddUserViewModel(application: Application) : AndroidViewModel(application) {

    private val userModificationRepository: UserModificationRepository =
        UserModificationRepositoryImpl(
            UserModificationLocalDataSourceImpl(application.applicationContext),
            UserModificationRemoteDataSourceImpl()
        )

    val errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorDialogMessage = MutableLiveData<String>().apply { value = "" }
    val errorCode = MutableLiveData<Int>().apply { value = NO_ERROR_TO_HANDLE }

    val openJobInputActivity = MutableLiveData<Boolean>().apply { value = false }

    lateinit var terms: Terms

    init {
        getTerms()
    }

    val allChecked = MutableLiveData<Boolean>().apply { value = true }
    val firstChecked = MutableLiveData<Boolean>().apply { value = true }
    val secondChecked = MutableLiveData<Boolean>().apply { value = true }
    val thirdChecked = MutableLiveData<Boolean>().apply { value = true }
    val fourthChecked = MutableLiveData<Boolean>().apply { value = true }

    private fun getTerms() {
        userModificationRepository.getTerms(
            onSuccess = { response ->
                terms = response
            },
            onFailure = { e ->
                errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
            }
        )
    }

    fun onAllCheckButtonChanged() {
        if (allChecked.value == true) {
            firstChecked.value = true
            secondChecked.value = true
            thirdChecked.value = true
            fourthChecked.value = true
        } else {
            firstChecked.value = false
            secondChecked.value = false
            thirdChecked.value = false
            fourthChecked.value = false
        }
    }

    fun onCheckButtonChanged() {
        allChecked.value =
            firstChecked.value == true
                    && secondChecked.value == true
                    && thirdChecked.value == true
                    && fourthChecked.value == true
    }

    fun startJobInputActivity() {
        if (firstChecked.value == true
            && secondChecked.value == true
        ) {
            openJobInputActivity.apply {
                value = true
                value = false
            }
        } else {
            errorDialogMessage.apply {
                value = Resources.getSystem().getString(R.string.essential_terms_guide)
                value = clearStringValue()
            }
        }
    }

    private fun clearStringValue(): String {
        return ""
    }

}