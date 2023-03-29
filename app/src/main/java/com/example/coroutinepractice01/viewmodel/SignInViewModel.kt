package com.example.coroutinepractice01.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinepractice01.repository.SignInError
import com.example.coroutinepractice01.repository.SignInRepository
import com.example.coroutinepractice01.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: SignInRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::SignInViewModel)
    }

    val tokenText = repository.userToken
    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun onSignInClick(emailText: String, passwordText: String) = launchDataLoad {
        repository.fetchUserToken(emailText, passwordText)
    }

    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                block()
            } catch (error: SignInError) {
                _snackBar.postValue(error.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}