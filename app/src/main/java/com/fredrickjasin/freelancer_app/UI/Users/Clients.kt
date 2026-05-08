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
import com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository

@Composable
fun ClientProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val factory = remember {

        ClientsViewModelFactory(
            ClientsRepository()
        )

    }

    val clientViewModel: ClientsViewModel =
        viewModel(factory = factory)

    val client by clientViewModel.client.collectAsState()

    val isLoading by
    clientViewModel.isLoading.collectAsState()

    val error by
    clientViewModel.error.collectAsState()

    val saved by
    clientViewModel.saved.collectAsState()


    LaunchedEffect(Unit) {

        clientViewModel.loadClient()

    }


    LaunchedEffect(saved) {

        if (saved){

            Toast.makeText(
                context,
                "Profile Saved Successfully",
                Toast.LENGTH_LONG
            ).show()

            clientViewModel.clearSavedState()

            navController.navigate(
                Routes.AddJobPage.name
            ){

                popUpTo(
                    Routes.ChoosePage.name
                ){

                    inclusive = true

                }

                launchSingleTop = true

            }

        }

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC)
                    )
                )
            )
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Complete Your Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Tell us about your company",
            color = Color.White.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = client.username,
                    onValueChange = {
                        clientViewModel.updateUsername(it)
                    },
                    label = {
                        Text("Display Name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = client.company,
                    onValueChange = {
                        clientViewModel.updateCompany(it)
                    },
                    label = {
                        Text("Company Name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = client.bio,
                    onValueChange = {
                        clientViewModel.updateBio(it)
                    },
                    label = {
                        Text("Business Bio")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                )

                OutlinedTextField(
                    value = client.location,
                    onValueChange = {
                        clientViewModel.updateLocation(it)
                    },
                    label = {
                        Text("Location")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {

                    Text(
                        text = it,
                        color = Color.Red
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        if (client.username.isNotEmpty()){

                            val userClient = Clients(

                                username = client.username,

                                company = client.company,

                                bio = client.bio,

                                location = client.location

                            )

                            clientViewModel
                                .saveClient(userClient)

                        }else{

                            Toast.makeText(
                                context,
                                "Enter username",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF2575FC)
                    )
                ) {

                    if (isLoading){

                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )

                    }else{

                        Text(
                            text = "Save Profile",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

        }

    }

}