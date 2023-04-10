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
            Log.v("Lance", "Result = ${result.api_key}")
            return  result
        } catch (error: Throwable){
            Log.v("Lance", "Sign in fail $error")
            throw NetworkError("Sign in fail", error)
        }
    }

    suspend fun fetchUserProfile(token: String): UserProfileResult{
        try {
            val result = withTimeout(networkTimeout) {
                network.getUserProfile(token)
            }
            Log.v("Lance", "Result = ${result.given_name}")
            return result
        } catch (error: Throwable) {
            Log.v("Lance", "Get user data fail $error")
            throw NetworkError("Get user data fail", error)
        }
    }
}
class NetworkError(message: String, cause: Throwable): Throwable(message, cause)