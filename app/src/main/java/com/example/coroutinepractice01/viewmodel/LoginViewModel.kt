package com.example.coroutinepractice01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinepractice01.data.LoginUiState
import com.example.coroutinepractice01.repository.LoginRepository
import com.example.coroutinepractice01.repository.NetworkError
import com.example.coroutinepractice01.util.singleArgViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::LoginViewModel)
    }

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    fun snackBarShown() {
        viewModelScope.launch {
            delay(5_000)
            _uiState.update { currentState ->
                currentState.copy(snackBarText = "")
            }
        }
    }

    fun onSignInClick(emailText: String, passwordText: String) = launchDataLoad {
        val loginResult = repository.fetchUserToken(emailText, passwordText)
        _uiState.update { currentState ->
            currentState.copy(isLogin = loginResult.api_key.isNotEmpty())
        }
        getUserProfile(loginResult.api_key)
    }

    private fun getUserProfile(token: String) = launchDataLoad {
        val userProfileResult = repository.fetchUserProfile(token)
        _uiState.update { currentState ->
            currentState.copy(userName = userProfileResult.given_name + " " + userProfileResult.family_name)
        }
    }


    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
                block()
            } catch (error: NetworkError) {
                _uiState.update { currentState ->
                    currentState.copy(snackBarText = error.message.toString())
                }
            } finally {
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false)
                }
            }
        }
    }
}