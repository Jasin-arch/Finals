package com.fredrickjasin.freelancer_app.UI.Users

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.data.Models.Profile
import com.fredrickjasin.freelancer_app.viewmodel.FreelancersViewModel
import com.fredrickjasin.freelancer_app.data.Repository.ProfilesRepository

@Composable
fun FreelancerProfileScreen(
    navController: NavHostController,
    viewModel: FreelancersViewModel = viewModel()
) {

    val context = LocalContext.current
    val viewModel = remember { FreelancersViewModel(ProfilesRepository()) }

    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saved by viewModel.saved.collectAsState()

    // Load profile once
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    // IMAGE PICKER
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.updateProfileImage(it.toString())
        }
    }

    // SUCCESS TOAST
    LaunchedEffect(saved) {
        if (saved) {
            Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
            viewModel.clearSavedState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
            .padding(pagepadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // HEADER
        Text(
            text = "Complete Your Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        )

        Card(
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Freelancer Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Username
                OutlinedTextField(
                    value = profile.username,
                    onValueChange = viewModel::updateUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Profession
                OutlinedTextField(
                    value = profile.profession,
                    onValueChange = viewModel::updateProfession,
                    label = { Text("Profession") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Bio
                OutlinedTextField(
                    value = profile.bio,
                    onValueChange = viewModel::updateBio,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                // Date of Birth
                OutlinedTextField(
                    value = profile.dateOfBirth,
                    onValueChange = viewModel::updateDOB,
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                // IMAGE BUTTON
                Button(
                    onClick = { imagePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB))
                ) {
                    Text("Select Profile Image", color = Color.White)
                }

                // IMAGE PREVIEW
                if (profile.profileImage.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(profile.profileImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                // LOCATION BUTTON
                Button(
                    onClick = { navController.navigate(Routes.MappickPage.name) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2575FC))
                ) {
                    Text(
                        if (profile.location.isEmpty()) "Select Location" else profile.location,
                        color = Color.White
                    )
                }

                // ERROR DISPLAY
                error?.let {
                    Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // SAVE BUTTON
                Button(
                    onClick = {
                        if (profile.username.isBlank() || profile.profession.isBlank()) {
                            Toast.makeText(context, "Fill required fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val userProfile = Profile(
                            username = profile.username,
                            profession = profile.profession,
                            bio = profile.bio,
                            profileImage = profile.profileImage,
                            location = profile.location,



                        )

                        viewModel.saveProfile(userProfile)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5C7D)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Save Profile", color = Color.White)
                    }
                }
            }
        }
    }
}