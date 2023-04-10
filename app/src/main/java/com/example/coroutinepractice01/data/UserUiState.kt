package com.example.coroutinepractice01.data
data class LoginUiState(
    val userName: String = "",
    val snackBarText: String = "",
    val isLoading: Boolean = false,
    val isLogin: Boolean = false
)
