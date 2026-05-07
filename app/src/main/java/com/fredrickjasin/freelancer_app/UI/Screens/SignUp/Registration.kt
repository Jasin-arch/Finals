package com.fredrickjasin.freelancer_app.UI.Screens.SignUp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.fredrickjasin.freelancer_app.UI.theme.Both
import com.fredrickjasin.freelancer_app.UI.theme.LogIn
import com.fredrickjasin.freelancer_app.UI.theme.SignUp

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registrationViewModel: RegistrationViewModel = viewModel()
) {

    val isLoading by registrationViewModel.isLoading.collectAsState()
    val message by registrationViewModel.message.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {

        if (message == "Account created successfully") {

            navController.navigate(Routes.ChoosePage.name) {

                popUpTo(Routes.SignUpPage.name) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottiAnimationWidget(R.raw.business, 250.dp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome SignUp to get Started",
            style = TextStyle(
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Both
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text("Username")
            },
            leadingIcon = {

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Both
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
                unfocusedBorderColor = Both
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email Address")
            },
            leadingIcon = {

                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                    tint = Both
                )
            },
            placeholder = {
                Text("user@gmail.com")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
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
                password = it
            },
            label = {
                Text("Enter Password")
            },
            leadingIcon = {

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.password_24),
                    contentDescription = null,
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
                        tint = SignUp
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
                unfocusedBorderColor = Both
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {

            CircularProgressIndicator()

        } else {

            OutlinedButton(
                onClick = {

                    val user = UserModel(
                        Email = email,
                        Password = password
                    )

                    registrationViewModel.registerUser(user)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Both
                )
            ) {

                Text(
                    text = "CREATE ACCOUNT",
                    color = LogIn
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = message
        )

        TextButton(
            onClick = {
                navController.navigate(Routes.LoginPage.name)
            }
        ) {

            Text("Already has an Account")
        }
    }
}