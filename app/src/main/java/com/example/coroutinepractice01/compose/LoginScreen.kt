package com.example.coroutinepractice01.compose

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coroutinepractice01.R
import com.example.coroutinepractice01.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")

@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel = viewModel()) {
    val loginUiState by viewModel.uiState.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val painterResource = painterResource(id = R.drawable.logo_pd)
            Image(
                painter = painterResource,
                contentDescription = "Logo",
                modifier = modifier
                    .fillMaxWidth(0.4f)
                    .weight(1f)
            )
            Text(
                text = if (loginUiState.userName.isNotEmpty()) "Welcome ${loginUiState.userName}" else "Not Login",
                modifier.weight(0.2f)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.weight(2f)
            ) {

                LoginOutLineTextFields(onLoginClick = { emailText, passwordText ->
                    viewModel.onSignInClick(emailText, passwordText)
                }, isLoading = loginUiState.isLoading)
            }
        }
        loginUiState.snackBarText.firstOrNull()?.let { snackBarText ->
            LaunchedEffect(snackBarText) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = loginUiState.snackBarText,
                    actionLabel = "Close App"
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> viewModel.snackBarShown()
                    SnackbarResult.ActionPerformed -> activity?.finish()
                }
            }
        }
    }


}

@Composable
fun LoginOutLineTextFields(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onLoginClick: (String, String) -> Unit,
) {
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp, 24.dp, 0.dp),
            value = emailText,
            onValueChange = { emailText = it },
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp, 24.dp, 0.dp),
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation =
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            trailingIcon = {
                val iconImage = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible)
                    "Hide Password"
                else "Show Password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = rememberVectorPainter(image = iconImage),
                        contentDescription = description
                    )
                }
            }
        )
        Button(
            onClick = { onLoginClick(emailText, passwordText) },
            enabled = !isLoading,
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = if (isLoading) "Processing" else "Login")
        }
    }
}