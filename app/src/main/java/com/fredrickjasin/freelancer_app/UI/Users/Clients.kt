package com.fredrickjasin.freelancer_app.UI.Users

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository
import io.github.jan.supabase.realtime.Column


@Composable
fun ClientProfileScreen(
    modifier: Modifier,
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
        uri?.let { viewModel.updateProfileImage(it.toString()) }
    }

    LaunchedEffect(Unit) { viewModel.loadClient() }

    LaunchedEffect(saved) {
        if (saved) {
            Toast.makeText(context, "Client profile saved", Toast.LENGTH_SHORT).show()
            viewModel.clearSavedState()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC))))
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
                modifier = Modifier.padding(20.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    "Client Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

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



                Button(
                    onClick = { imagePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB))
                ) { Text("Select Profile Image", color = Color.White) }

                if (client.profileImage.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(client.profileImage),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
                    )
                }

                error?.let { Text(it, color = Color.Red) }

                Button(
                    onClick = {
                        val userClients = Clients(
                            username = client.username,
                            company = client.company,
                            bio = client.bio,
                            profileImage = client.profileImage,
                            location = client.location


                        )
                        viewModel.saveClient(userClients)
                              },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5C7D)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    if (isLoading) CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    else Text("Save Profile", color = Color.White)
                }
            }
        }
    }
}