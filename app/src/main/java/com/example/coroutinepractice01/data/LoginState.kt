package com.example.coroutinepractice01.data


data class LoginPara(val email: String, val password: String, )
data class LoginState(
    val api_key: String? = "",
    val id: Int = 0,
    val snackBarText: String = "",
    val isLoading: Boolean = false
)
