package com.bnvs.metaler.view.termsagree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.user.modification.model.Terms
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE

class ViewModelTermsAgree(
    private val userModificationRepository: UserModificationRepository
) : BaseViewModel() {

    private val _openJobInputActivity = MutableLiveData<Boolean>().apply { value = false }
    val openJobInputActivity: LiveData<Boolean> = _openJobInputActivity

    private val _terms = MutableLiveData<Terms>()
    val terms: LiveData<Terms> = _terms

    init {
        getTerms()
    }

    // TermsAgree Buttons
    private val _allChecked = MutableLiveData<Boolean>().apply { value = true }
    val allChecked: LiveData<Boolean> = _allChecked
    private val _firstChecked = MutableLiveData<Boolean>().apply { value = true }
    val firstChecked: LiveData<Boolean> = _firstChecked
    private val _secondChecked = MutableLiveData<Boolean>().apply { value = true }
    val secondChecked: LiveData<Boolean> = _secondChecked
    private val _thirdChecked = MutableLiveData<Boolean>().apply { value = true }
    val thirdChecked: LiveData<Boolean> = _thirdChecked
    private val _fourthChecked = MutableLiveData<Boolean>().apply { value = true }
    val fourthChecked: LiveData<Boolean> = _fourthChecked

    private fun getTerms() {
        userModificationRepository.getTerms(
            onSuccess = { response ->
                _terms.value = response
            },
            onFailure = { e ->
                _errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                _errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
            }
        )
    }

    fun onAllCheckButtonChanged() {
        if (allChecked.value == false) {
            _allChecked.value = true
            _firstChecked.value = true
            _secondChecked.value = true
            _thirdChecked.value = true
            _fourthChecked.value = true
        } else {
            _allChecked.value = false
            _firstChecked.value = false
            _secondChecked.value = false
            _thirdChecked.value = false
            _fourthChecked.value = false
        }
    }

    fun onCheckButtonChanged(buttonIndex: Int) {
        when (buttonIndex) {
            1 -> {
                _firstChecked.value = firstChecked.value != true
            }
            2 -> {
                _secondChecked.value = secondChecked.value != true
            }
            3 -> {
                _thirdChecked.value = thirdChecked.value != true
            }
            4 -> {
                _fourthChecked.value = fourthChecked.value != true
            }
        }

        _allChecked.value =
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
        _openJobInputActivity.apply {
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
            _errorDialogMessage.apply {
                value = "메탈러 서비스 이용을 위해서 필수 이용약관 동의가 필요합니다."
                value = clearStringValue()
            }
        }
    }
}