package com.example.coroutinepractice01.api

import com.example.coroutinepractice01.BuildConfig
import com.example.coroutinepractice01.data.LoginResult
import com.example.coroutinepractice01.data.UserProfileResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

private val service: MainNetwork by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MainNetwork::class.java)
}

fun getNetworkService() = service

interface MainNetwork {
    @POST("user/signin")
    suspend fun signIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginResult

    @POST("user/profile")
    suspend fun getUserProfile(@Query("api_key") apiKey: String): UserProfileResult
}