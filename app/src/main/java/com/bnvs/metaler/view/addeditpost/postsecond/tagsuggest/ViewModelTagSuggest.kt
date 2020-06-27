package com.bnvs.metaler.view.addeditpost.postsecond.tagsuggest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.source.repository.TagsRepository
import com.bnvs.metaler.network.NetworkUtil

class ViewModelTagSuggest(
    private val tagsRepository: TagsRepository
) : ViewModel() {

    private val _tagSuggestions = MutableLiveData<List<String>>()
    val tagSuggestions: LiveData<List<String>> = _tagSuggestions
    private val _errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorToastMessage: LiveData<String> = _errorToastMessage

    fun getTagSuggestions(typeStr: String, input: String) {
        val type: Int = when (typeStr) {
            "store" -> 1
            "work" -> 2
            "etc" -> 3
            else -> -1
        }
        tagsRepository.getTagRecommendations(
            TagsRequest(type, input, 10),
            onSuccess = { response ->
                _tagSuggestions.value = response
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    private fun MutableLiveData<String>.setMessage(message: String) {
        this.apply {
            value = message
            value = clearStringValue()
        }
    }

    private fun clearStringValue(): String {
        return ""
    }

}