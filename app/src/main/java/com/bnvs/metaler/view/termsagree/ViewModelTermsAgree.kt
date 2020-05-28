package com.bnvs.metaler.view.termsagree

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepositoryImpl
import com.bnvs.metaler.network.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.network.NetworkUtil

class ViewModelTermsAgree(application: Application) : AndroidViewModel(application) {

    private val userModificationRepository: UserModificationRepository =
        UserModificationRepositoryImpl(
            UserModificationLocalDataSourceImpl(application.applicationContext),
            UserModificationRemoteDataSourceImpl()
        )

    val errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorDialogMessage = MutableLiveData<String>().apply { value = "" }
    val errorCode = MutableLiveData<Int>().apply { value = NO_ERROR_TO_HANDLE }

    val openJobInputActivity = MutableLiveData<Boolean>().apply { value = false }

    val terms = MutableLiveData<Terms>()

    init {
        getTerms()
    }

    // TermsAgree Buttons
    val allChecked = MutableLiveData<Boolean>().apply { value = true }
    val firstChecked = MutableLiveData<Boolean>().apply { value = true }
    val secondChecked = MutableLiveData<Boolean>().apply { value = true }
    val thirdChecked = MutableLiveData<Boolean>().apply { value = true }
    val fourthChecked = MutableLiveData<Boolean>().apply { value = true }

    private fun getTerms() {
        userModificationRepository.getTerms(
            onSuccess = { response ->
                terms.value = response
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
        if (allChecked.value == false) {
            allChecked.value = true
            firstChecked.value = true
            secondChecked.value = true
            thirdChecked.value = true
            fourthChecked.value = true
        } else {
            allChecked.value = false
            firstChecked.value = false
            secondChecked.value = false
            thirdChecked.value = false
            fourthChecked.value = false
        }
    }

    fun onCheckButtonChanged(buttonIndex: Int) {
        when (buttonIndex) {
            1 -> {
                firstChecked.value = firstChecked.value != true
            }
            2 -> {
                secondChecked.value = secondChecked.value != true
            }
            3 -> {
                thirdChecked.value = thirdChecked.value != true
            }
            4 -> {
                fourthChecked.value = fourthChecked.value != true
            }
        }

        allChecked.value =
            firstChecked.value == true
                    && secondChecked.value == true
                    && thirdChecked.value == true
                    && fourthChecked.value == true
    }

    private fun saveTermsAgreements() {
        userModificationRepository.saveTermsAgreements(
            TermsAgreements(
                firstChecked.value ?: false,
                secondChecked.value ?: false,
                thirdChecked.value ?: false,
                fourthChecked.value ?: false
            )
        )
    }

    private fun startJobInputActivity() {
        openJobInputActivity.apply {
            value = true
            value = false
        }
    }


    fun completeTermsAgree() {
        if (firstChecked.value == true
            && secondChecked.value == true
        ) {
            saveTermsAgreements()
            startJobInputActivity()
        } else {
            errorDialogMessage.apply {
                value = "메탈러 서비스 이용을 위해서 필수 이용약관 동의가 필요합니다."
                value = clearStringValue()
            }
        }
    }

    private fun clearStringValue(): String {
        return ""
    }

}