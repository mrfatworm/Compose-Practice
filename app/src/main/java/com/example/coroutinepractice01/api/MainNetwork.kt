package com.example.coroutinepractice01.api

import com.example.coroutinepractice01.data.SignInToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val service: MainNetwork by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://bctest.westeurope.cloudapp.azure.com/brandcloud-dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MainNetwork::class.java)
}

fun getNetworkService() = service

interface MainNetwork {
    @POST("user/signin")
    suspend fun signIn(@Body email: String, @Body password: String): SignInToken
}