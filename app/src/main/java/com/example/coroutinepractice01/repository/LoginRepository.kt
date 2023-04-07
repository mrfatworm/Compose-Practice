package com.example.coroutinepractice01.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coroutinepractice01.api.MainNetwork
import com.example.coroutinepractice01.data.LoginPara
import kotlinx.coroutines.withTimeout

class LoginRepository (private val network: MainNetwork) {

    private val _userToken = MutableLiveData<String?>()
    val userToken: LiveData<String?>
        get() = _userToken

    suspend fun fetchUserToken(emailText: String, passwordText: String): String{
        try {
            val result = withTimeout(5000) {
                network.signIn(LoginPara(emailText, passwordText))
            }
            Log.v("Lance", "Result = ${result.api_key}")
            return  result.api_key.toString()
        } catch (error: Throwable){
            Log.v("Lance", "Sign in fail $error")
            throw SignInError("Sign in fail", error)
        }
    }
}
class SignInError(message: String, cause: Throwable): Throwable(message, cause)