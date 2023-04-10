package com.example.coroutinepractice01.data

data class LoginResult(val api_key: String, val id: Int)

data class UserProfileResult(
    val id: Int,
    val email: String,
    val given_name: String,
    val family_name: String,
    val picture: String
)
