package com.fredrickjasin.freelancer_app.UI.Screens.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.R
import com.fredrickjasin.freelancer_app.UI.components.LottiAnimationWidget
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.UI.theme.Both
import com.fredrickjasin.freelancer_app.UI.theme.ForgotPassword
import com.fredrickjasin.freelancer_app.UI.theme.LogIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(success) {

        if (success) {

            navController.navigate(Routes.ChoosePage.name) {

                popUpTo(Routes.LoginPage.name) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(pagepadding)
    ) {

        LottiAnimationWidget(R.raw.business, 250.dp)

        Text(
            text = "LOGIN TO GET STARTED",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                viewModel.onEmailChange(it)
            },
            label = {
                Text("Enter Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email",
                    tint = Both
                )
            },
            placeholder = {
                Text("user@example.com")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LogIn,
                unfocusedBorderColor = Both
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                viewModel.onPasswordChange(it)
            },
            label = {
                Text("Enter Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.password_24),
                    contentDescription = "Password",
                    tint = Both
                )
            },
            visualTransformation =
                if (isVisible) VisualTransformation.None
                else PasswordVisualTransformation(),

            trailingIcon = {

                IconButton(
                    onClick = {
                        isVisible = !isVisible
                    }
                ) {

                    Icon(
                        imageVector =
                            if (isVisible)
                                ImageVector.vectorResource(R.drawable.visibility_off_24)
                            else
                                ImageVector.vectorResource(R.drawable.visibale_24),

                        contentDescription = null,
                        tint = Both
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LogIn,
                unfocusedBorderColor = Both
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = {
                viewModel.loginUser()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Both,
                contentColor = Color.White
            )
        ) {

            Text(
                text = "LOGIN",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {

            CircularProgressIndicator()

        }

        error?.let {

            Text(
                text = it,
                color = Color.Red
            )
        }

        if (success) {

            Text(
                text = "Login Successful",
                color = ForgotPassword
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {

            TextButton(
                onClick = {
                    navController.navigate(
                        Routes.ForgotPasswordPage.name
                    )
                }
            ) {

                Text(
                    text = "Forgot Password",
                    color = LogIn
                )
            }

            TextButton(
                onClick = {
                    navController.navigate(
                        Routes.SignUpPage.name
                    )
                }
            ) {

                Text(
                    text = "No account?",
                    color = LogIn
                )
            }
        }
    }
}