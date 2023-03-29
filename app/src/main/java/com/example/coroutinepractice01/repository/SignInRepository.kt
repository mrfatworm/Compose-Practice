package com.example.coroutinepractice01.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coroutinepractice01.api.MainNetwork
import kotlinx.coroutines.withTimeout

class SignInRepository (private val network: MainNetwork) {

    private val _userToken = MutableLiveData<String?>()
    val userToken: LiveData<String?>
        get() = _userToken

    suspend fun fetchUserToken(emailText: String, passwordText: String){
        try {
            val result = withTimeout(5000) {
                network.signIn(emailText, passwordText)
            }
            _userToken.postValue(result.api_key)
        } catch (error: Throwable){
            throw SignInError("Sign in fail", error)
        }
    }
}
class SignInError(message: String, cause: Throwable): Throwable(message, cause)