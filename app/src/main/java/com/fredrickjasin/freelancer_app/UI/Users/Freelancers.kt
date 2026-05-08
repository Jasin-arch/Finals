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
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes

@Composable
fun FreelancerProfileScreen(
    navController: NavHostController,
    userId: String,
    viewModel: FreelancersViewModel, // Updated ViewModel name here
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saved by viewModel.saved.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
    }

    LaunchedEffect(saved) {
        if (saved) {
            Toast.makeText(context, "Profile Saved Successfully", Toast.LENGTH_SHORT).show()
            viewModel.clearSavedState()

            navController.navigate(Routes.HomePage.name) {
                popUpTo(0) { inclusive = true }
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
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                OutlinedTextField(
                    value = profile.username,
                    onValueChange = viewModel::updateUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.profession,
                    onValueChange = viewModel::updateProfession,
                    label = { Text("Profession") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.bio,
                    onValueChange = viewModel::updateBio,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.dateOfBirth,
                    onValueChange = viewModel::updateDOB,
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = profile.location,
                    onValueChange = viewModel::updateLocation,
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Rating: ⭐ ${profile.rating}", fontWeight = FontWeight.Medium, color = Color.DarkGray)
                    Text("Jobs: ${profile.completedJobs}", fontWeight = FontWeight.Medium, color = Color.DarkGray)
                }

                error?.let {
                    Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB))
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Save Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}