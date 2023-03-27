package com.example.coroutinepractice01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
fun LoginOutLineTextFields(modifier: Modifier = Modifier) {
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = emailText,
            onValueChange = { emailText = it },
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation =
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
    }
}

@Composable
fun PositiveNegativeRowButton(
    positiveText: String = "Yes",
    negativeText: String = "No",
    positiveClicked: () -> Unit,
    negativeClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        OutlinedButton(
            onClick = { negativeClicked },
            Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = negativeText)
        }
        Button(onClick = { positiveClicked }, Modifier.weight(1f)) {
            Text(text = positiveText)
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painterResource = painterResource(id = R.drawable.logo_pd)
        Image(
            painter = painterResource,
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth(0.4f)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginOutLineTextFields()
            PositiveNegativeRowButton(
                positiveClicked = { /*TODO*/ },
                positiveText = "Login",
                negativeText = "Sign Up"
            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutinePractice01Theme {
        LoginPage()
    }
}