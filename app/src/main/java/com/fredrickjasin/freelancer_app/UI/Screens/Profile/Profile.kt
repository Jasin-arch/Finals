package com.fredrickjasin.freelancer_app.UI.Screens.Profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.UI.theme.Both
import com.fredrickjasin.freelancer_app.UI.theme.LogIn
import com.fredrickjasin.freelancer_app.viewmodel.FreelancersViewModel

@Composable
fun ProfileScreen(
    navController: androidx.navigation.NavHostController,
    viewModel: FreelancersViewModel = viewModel()
) {

    val context = LocalContext.current

    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saved by viewModel.saved.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(saved) {

        if (saved) {

            Toast.makeText(
                context,
                "Profile Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearSavedState()

            navController.navigate(Routes.HomePage.name) {
                popUpTo(Routes.HomePage.name) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC)
                    )
                )
            )
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "My Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = profile.username,
                    onValueChange = {
                        viewModel.updateUsername(it)
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
                        focusedBorderColor = LogIn,
                        unfocusedBorderColor = Both
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = profile.profession,
                    onValueChange = {
                        viewModel.updateProfession(it)
                    },
                    label = {
                        Text("Profession")
                    },
                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Both
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LogIn,
                        unfocusedBorderColor = Both
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = profile.bio,
                    onValueChange = {
                        viewModel.updateBio(it)
                    },
                    label = {
                        Text("Bio")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LogIn,
                        unfocusedBorderColor = Both
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = profile.location,
                    onValueChange = {
                        viewModel.updateLocation(it)
                    },
                    label = {
                        Text("Location")
                    },
                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = Both
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LogIn,
                        unfocusedBorderColor = Both
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {

                    Text(
                        text = it,
                        color = Color.Red
                    )
                }

                Button(
                    onClick = {

                        if (
                            profile.username.isBlank() ||
                            profile.profession.isBlank()
                        ) {

                            Toast.makeText(
                                context,
                                "Please fill all required fields",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        viewModel.saveProfile()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Both
                    )
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            color = Color.White
                        )

                    } else {

                        Text(
                            text = "Update Profile",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
