package com.kabindra.sample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.sample.db.model.UserData
import com.kabindra.sample.repository.user.UserRepository
import com.kabindra.sample.util.apiHandle.ApiResult
import com.kabindra.sample.util.apiHandle.SingleLiveEvent
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val showLoading = SingleLiveEvent<Boolean>()
    val showError = SingleLiveEvent<String>()
    val userList = MutableLiveData<List<UserData>>()

    fun getUser() {
        showLoading.value = true
        viewModelScope.launch {
            val result = repository.getUser()

            showLoading.value = false
            when (result) {
                is ApiResult.Success -> {
                    userList.value = result.successData
                    showError.value = null
                }
                is ApiResult.Error -> showError.value = result.exception.message
            }
        }
    }
}