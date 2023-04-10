package com.example.coroutinepractice01.data
data class LoginUiState(
    val userName: String = "Unknown User",
    val snackBarText: String = "",
    val isLoading: Boolean = false,
    val isLogin: Boolean = false
)
