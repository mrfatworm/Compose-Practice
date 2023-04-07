package com.example.coroutinepractice01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinepractice01.api.getNetworkService
import com.example.coroutinepractice01.compose.LoginScreen
import com.example.coroutinepractice01.repository.LoginRepository
import com.example.coroutinepractice01.ui.theme.CoroutinePractice01Theme
import com.example.coroutinepractice01.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = LoginRepository(getNetworkService())
        val viewModel = ViewModelProvider(
            this,
            LoginViewModel.FACTORY(repository)
        ).get(LoginViewModel::class.java)

        setContent {
            CoroutinePractice01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }
}