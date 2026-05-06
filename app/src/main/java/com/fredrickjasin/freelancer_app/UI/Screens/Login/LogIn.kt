package com.fredrickjasin.freelancer_app.UI.Screens.Login

import android.widget.MediaController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.R
import com.fredrickjasin.freelancer_app.UI.components.LottiAnimationWidget
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.ui.theme.Both
import com.fredrickjasin.freelancer_app.ui.theme.ForgotPassword
import com.fredrickjasin.freelancer_app.ui.theme.LogIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: LoginViewModel = viewModel()
    ) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()
    var EmailInput by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var isvisibile by remember { mutableStateOf(false) }



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(pagepadding)
    )
    {
//        lottie Anime
        LottiAnimationWidget(R.raw.business, 250.dp)
//   welcoming text
        Text(
            text = "LOGIN TO GET STARTED",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

//            Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text(text = "Enter Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email",
                    tint = Both,
                )
            },
            placeholder = {
                Text(text = "E.g User@Example.com")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LogIn,
                unfocusedBorderColor = Both
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
//        password Input
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text(text = "Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.password_24),
                    contentDescription = "Password",
                    tint = Both,
                )
            },
            visualTransformation = if (isvisibile) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = { isvisibile = !isvisibile }
                ) {
                    if (isvisibile) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.visibility_off_24),
                            contentDescription = "Password",
                            tint = Both,
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.visibale_24),
                            contentDescription = "Password",
                            tint = Both,
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LogIn,
                unfocusedBorderColor = Both
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
//        button
        OutlinedButton(
            onClick = {
                viewModel.loginUser()
                },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFFFFFF),
                containerColor = Both
            )
        ) {
            Text(
                text = "login",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForgotPassword
                ),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

        }
        if (isLoading) {
            CircularProgressIndicator()
        }

        error?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = it,
                color = Color.Red
            )
        }

        if (success) {
            Text(
                text = "Login successful!",
                color = ForgotPassword
            )
        }

        LaunchedEffect(success) {
            if (success) {
                navController.navigate(Routes.ChoosePage.name) {
                    popUpTo(Routes.LoginPage.name) { inclusive = true }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row {
            TextButton(
                onClick = { navController.navigate(Routes.ForgotPasswordPage.name) }
            ) {
                Text(
                    text = "Forgot Password",
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
                    text = "No account?",
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



