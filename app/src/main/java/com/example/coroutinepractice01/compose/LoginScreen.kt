package com.example.coroutinepractice01.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coroutinepractice01.R
import com.example.coroutinepractice01.viewmodel.LoginViewModel

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel = viewModel()) {
    val loginUiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
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
        Text(text = loginUiState.api_key ?: "No Data", modifier.weight(0.2f))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.weight(2f)
        ) {

            LoginOutLineTextFields(onLoginClick = { emailText, passwordText ->
                viewModel.onSignInClick(emailText, passwordText)
            })
        }
    }
}

@Composable
fun LoginOutLineTextFields(
    modifier: Modifier = Modifier,
    onLoginClick: (String, String) -> Unit
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
            onClick = { onLoginClick(emailText, passwordText) }, modifier = modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = "Login")
        }
    }
}