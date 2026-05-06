package com.fredrickjasin.freelancer_app.UI.Screens.ForgotPassword

import android.widget.MediaController
import androidx.compose.runtime.Composable
import com.fredrickjasin.freelancer_app.R
import com.fredrickjasin.freelancer_app.UI.components.LottiAnimationWidget
import com.fredrickjasin.freelancer_app.ui.theme.Both
import com.fredrickjasin.freelancer_app.ui.theme.ForgotPassword
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.ui.theme.LogIn

@Composable
fun ForgotPasswordScreen(navController: NavHostController, modifier: Modifier) {
    val viewModel = remember { ForgotPasswordViewModel() }
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    val email by viewModel.email.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.successMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

//    Structure
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
//    lottie Anime
        LottiAnimationWidget(R.raw.business, 300.dp)
        Spacer(modifier = Modifier.height(24.dp))

//    page Notes
        Text(
            text = "Oops! Forgot Password ?",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
//    email input
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Input",
                    tint = LogIn,

                    )
            },
            placeholder = {
                Text(
                    text = "User@gmail.com"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ForgotPassword,
                unfocusedBorderColor = Both
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
//    submit button
        OutlinedButton(
            onClick = {
                viewModel.resetPassword()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFFFFFF),
                containerColor = Both
            )
        ) {
            Text("GET PASSWORD")
        }
        if (error != null) {
            Text(text = error!!, color = Color.Red)
        }

        if (success != null) {
            Text(text = success!!, color = Color.Green)
        }
        Spacer(modifier = Modifier.height(24.dp))

//    text button
        Row {
            TextButton(
                onClick = { navController.navigate(Routes.LoginPage.name) }
            ) {
                Text(
                    text = "Back to Login",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = LogIn
                    ),
                    )
            }

            TextButton(
                onClick = { navController.navigate(Routes.SignUpPage.name) }
            ) {
                Text(
                    text = "Create New Account",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = LogIn
                    ),
                )
            }
        }
    }

}

