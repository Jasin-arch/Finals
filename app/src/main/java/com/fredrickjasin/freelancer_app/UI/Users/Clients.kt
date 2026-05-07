package com.fredrickjasin.freelancer_app.UI.Users

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository
import com.fredrickjasin.freelancer_app.viewmodel.ClientsViewModel

@Composable
fun ClientProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current

    val viewModel = remember { ClientsViewModel(ClientsRepository()) }

    val client by viewModel.client.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saved by viewModel.saved.collectAsState()

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.updateProfileImage(it.toString())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadClient()
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

        Text(
            text = "Client Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 20.dp)
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

                Text("Client Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))

                OutlinedTextField(
                    value = client.username,
                    onValueChange = viewModel::updateUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = client.company,
                    onValueChange = viewModel::updateCompany,
                    label = { Text("Company") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = client.bio,
                    onValueChange = viewModel::updateBio,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = client.dateOfBirth,
                    onValueChange = viewModel::updateDOB,
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { imagePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB))
                ) {
                    Text(text = "Select Profile Image")
//                    Text("Select Profile Image", color = Color)
                }
            }
        }
    }
}