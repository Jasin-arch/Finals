package com.fredrickjasin.freelancer_app.UI.Users

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.data.Models.Clients

@Composable
fun ClientProfileScreen(
    navController: NavHostController,
    userId: String,
    modifier: Modifier = Modifier,
    // Change this line:
    clientViewModel: ClientsViewModel = viewModel(
        factory = ClientsViewModelFactory(com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository())
    )
) {
    // ... rest of your code ...

    val context = LocalContext.current

    // Observe state from ViewModel
    val client by clientViewModel.client.collectAsState()
    val isLoading by clientViewModel.isLoading.collectAsState()
    val error by clientViewModel.error.collectAsState()
    val saved by clientViewModel.saved.collectAsState()

    // 1. Load data as soon as the screen is displayed
    LaunchedEffect(Unit) {
        clientViewModel.loadClient()
    }

    // 2. Handle the "Saved" state to navigate away
    LaunchedEffect(saved) {
        if (saved) {
            Toast.makeText(context, "Profile Created Successfully!", Toast.LENGTH_SHORT).show()
            clientViewModel.clearSavedState()

            navController.navigate(Routes.HomePage.name) {
                // Clear the stack so user can't "back" into the profile creation
                popUpTo(Routes.ChoosePage.name) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Complete Your Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tell us about your business",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        /* --- INPUT FORM CARD --- */
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Username
                OutlinedTextField(
                    value = client.username,
                    onValueChange = clientViewModel::updateUsername,
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Company
                OutlinedTextField(
                    value = client.company,
                    onValueChange = clientViewModel::updateCompany,
                    label = { Text("Company Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Bio
                OutlinedTextField(
                    value = client.bio,
                    onValueChange = clientViewModel::updateBio,
                    label = { Text("About your business") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                // Location
                OutlinedTextField(
                    value = client.location,
                    onValueChange = clientViewModel::updateLocation,
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Error Feedback
                error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // SAVE BUTTON
                Button(
                    onClick = {
                        if (client.username.isNotEmpty()) {
                            val userClients = Clients(
                                username = client.username,
                                company = client.company,
                                bio = client.bio,
                                location = client.location
                            )
                            clientViewModel.saveClient(userClients)
                        } else {
                            Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2575FC)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Get Started",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}