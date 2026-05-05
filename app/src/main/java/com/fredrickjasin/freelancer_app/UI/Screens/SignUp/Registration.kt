package com.fredrickjasin.freelancer_app.UI.Screens.SignUp


import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.fredrickjasin.freelancer_app.ui.theme.Both
import com.fredrickjasin.freelancer_app.ui.theme.ForgotPassword
import com.fredrickjasin.freelancer_app.ui.theme.LogIn
import com.fredrickjasin.freelancer_app.ui.theme.SignUp


@Composable
fun RegistrationScreen(
    navController: NavHostController,
   modifier: Modifier,
    RegistrationViewModel: RegistrationViewModel = viewModel()

   ) {
    val isLoading = RegistrationViewModel.isLoading.collectAsState()
    val responseMessage = RegistrationViewModel.message.collectAsState()
    var nameInput by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var isvisibile by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        lottie Anime
        LottiAnimationWidget(R.raw.business, 250.dp)
        Spacer(modifier = Modifier.height(24.dp))

//        page notes
        Text(
            text = "Welcome SignUp to get Started",
            style = TextStyle(
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Both,
            )
        )

//      Name Section
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Email Input",
                    tint = Both,

                    )
            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
                unfocusedBorderColor = Both
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(24.dp))

//        Email Section

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Input",
                    tint = Both,

                    )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
                unfocusedBorderColor = Both
            ),
            placeholder = {
                Text(
                    text = "User@gmail.com"
                )
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

//        Password Section
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(text = "Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(com.fredrickjasin.freelancer_app.R.drawable.password_24),
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
                            imageVector = ImageVector.vectorResource(com.fredrickjasin.freelancer_app.R.drawable.visibility_off_24),
                            contentDescription = "Password",
                            tint = SignUp,
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(com.fredrickjasin.freelancer_app.R.drawable.visibale_24),
                            contentDescription = "Password",
                            tint = SignUp,
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SignUp,
                unfocusedBorderColor = Both
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Text(text = responseMessage.value)
        Text(text = isLoading.value.toString())
        HorizontalDivider()
        if(isLoading.value){
            CircularProgressIndicator()
        }else{
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        Email = email.text,
                        Password = passwordInput.text
                    )
                    RegistrationViewModel.registerUser(user)
                }
            ) {
                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = ForgotPassword,
                        containerColor = Both
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = "CREATE ACCOUNT ",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LogIn
                        ),
                        modifier = Modifier.padding(horizontal = 24.dp),
                    )
                }
            }
        }


        TextButton(
            onClick = { navController.navigate(Routes.LoginPage.name) }
        ) {
            Text(
                text = "Already has an Account",
                style = TextStyle(
                    fontSize = 11.sp
                ),
            )




        }
    }
}
