package com.fredrickjasin.freelancer_app.UI.Users

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.viewmodel.FreelancersViewModel

@Composable
fun FreelancerProfileScreen(
    navController: NavHostController,
    userId: String,
    viewModel: FreelancersViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saved by viewModel.saved.collectAsState()

    // Load profile data when screen opens
    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
    }

    // Handle Navigation Side Effect when 'saved' becomes true
    LaunchedEffect(saved) {
        if (saved) {
            Toast.makeText(context, "Profile Saved Successfully", Toast.LENGTH_SHORT).show()

            // Reset the 'saved' state in ViewModel so the toast/nav doesn't loop
            viewModel.clearSavedState()

            // Navigate to Home and clear the profile screen from the backstack
            navController.navigate(Routes.HomePage.name) {
                // This clears everything up to the Home Page,
                // preventing the user from navigating back to the edit screen
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Freelancer Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = profile.username,
                    onValueChange = viewModel::updateUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = profile.profession,
                    onValueChange = viewModel::updateProfession,
                    label = { Text("Profession") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = profile.bio,
                    onValueChange = viewModel::updateBio,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = profile.dateOfBirth,
                    onValueChange = viewModel::updateDOB,
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = profile.location,
                    onValueChange = viewModel::updateLocation,
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Rating: ⭐ ${profile.rating}", fontWeight = FontWeight.Medium)
                    Text("Jobs: ${profile.completedJobs}", fontWeight = FontWeight.Medium)
                }

                error?.let {
                    Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (profile.username.isBlank() || profile.profession.isBlank()) {
                            Toast.makeText(
                                context,
                                "Please fill in required fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.saveProfile()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoading // Disable button while saving
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Save Profile", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
