package com.example.coroutinepractice01.repository

import com.example.coroutinepractice01.api.MainNetwork
import com.example.coroutinepractice01.data.ErrorResult
import com.example.coroutinepractice01.data.LoginResult
import com.example.coroutinepractice01.data.UserProfileResult
import com.google.gson.Gson
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException

class LoginRepository (private val network: MainNetwork) {

    private val networkTimeout = 5_000L

    suspend fun fetchUserToken(emailText: String, passwordText: String): LoginResult{
        try {
            val result = withTimeout(networkTimeout) {
                network.signIn(emailText, passwordText)
            }
            return  result
        } catch (error: Throwable){
            if (error is HttpException){
                val errorBody = error.response()?.errorBody()?.string()
                val errorResult: ErrorResult = Gson().fromJson(errorBody, ErrorResult::class.java)
                throw NetworkError(errorResult.error_name)
            } else {
                throw NetworkError("Unknown Error")
            }
        }
    }

    suspend fun fetchUserProfile(token: String): UserProfileResult{
        try {
            val result = withTimeout(networkTimeout) {
                network.getUserProfile(token)
            }
            return result
        }  catch (error: Throwable){
            if (error is HttpException){
                val errorBody = error.response()?.errorBody()?.string()
                val errorResult: ErrorResult = Gson().fromJson(errorBody, ErrorResult::class.java)
                throw NetworkError(errorResult.error_name)
            } else {
                throw NetworkError("Unknown Error")
            }
        }
    }
}
class NetworkError(message: String): Throwable(message)