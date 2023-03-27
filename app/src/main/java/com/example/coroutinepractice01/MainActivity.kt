package com.example.coroutinepractice01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.coroutinepractice01.ui.theme.CoroutinePractice01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinePractice01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginPage()
                }
            }
        }
    }
}

@Composable
fun LoginOutLineTextFields(
    onLoginClick: (String, String) -> Unit
) {
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp, 24.dp, 0.dp),
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
            modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp, 24.dp, 0.dp),
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
            onClick = { onLoginClick(emailText, passwordText) }, modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = "Login")
        }
    }
}

@Composable
fun LoginPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val painterResource = painterResource(id = R.drawable.logo_pd)
        Image(
            painter = painterResource,
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth(0.4f).weight(1f)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(2f)
        ) {

            LoginOutLineTextFields(onLoginClick = { emailText, passwordText ->
                onLogin(emailText, passwordText)
            })
        }
    }
}

private fun onLogin(emailText: String, passwordText: String) {
    println("Email = $emailText")
    println("Password = $passwordText")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutinePractice01Theme {
        LoginPage()
    }
}