package com.example.coroutinepractice01.repository

import android.util.Log
import com.example.coroutinepractice01.api.MainNetwork
import com.example.coroutinepractice01.data.LoginResult
import com.example.coroutinepractice01.data.UserProfileResult
import kotlinx.coroutines.withTimeout

class LoginRepository (private val network: MainNetwork) {

    private val networkTimeout = 5_000L

    suspend fun fetchUserToken(emailText: String, passwordText: String): LoginResult{
        try {
            val result = withTimeout(networkTimeout) {
                network.signIn(emailText, passwordText)
            }
            return  result
        } catch (error: Throwable){
            throw NetworkError("Sign in fail", error)
        }
    }

    suspend fun fetchUserProfile(token: String): UserProfileResult{
        try {
            val result = withTimeout(networkTimeout) {
                network.getUserProfile(token)
            }
            return result
        } catch (error: Throwable) {
            throw NetworkError("Get user data fail", error)
        }
    }
}
class NetworkError(message: String, cause: Throwable): Throwable(message, cause)