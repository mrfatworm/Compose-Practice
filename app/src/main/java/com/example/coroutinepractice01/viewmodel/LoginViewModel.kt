package com.example.coroutinepractice01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinepractice01.data.LoginState
import com.example.coroutinepractice01.repository.LoginRepository
import com.example.coroutinepractice01.repository.SignInError
import com.example.coroutinepractice01.util.singleArgViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::LoginViewModel)
    }

    private val _uiState = MutableStateFlow(LoginState())
    val uiState : StateFlow<LoginState> = _uiState.asStateFlow()

    fun onSignInClick(emailText: String, passwordText: String) = launchDataLoad {
        val apiKey = repository.fetchUserToken(emailText, passwordText)
        _uiState.update { currentState ->
            currentState.copy(api_key = apiKey)
        }
    }


    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
                block()
            } catch (error: SignInError) {
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