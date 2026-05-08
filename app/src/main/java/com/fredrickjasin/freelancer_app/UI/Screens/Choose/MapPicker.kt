package com.fredrickjasin.freelancer_app.UI.Screens.Choose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MapPickerScreen(
    modifier: Modifier,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Select Your Location",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "⚠️ (This is a placeholder map screen)\nReplace with Google Maps later",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                returnLocation(navController, "Nairobi, Kenya")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Nairobi")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                returnLocation(navController, "Mombasa, Kenya")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Mombasa")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                returnLocation(navController, "Kisumu, Kenya")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Kisumu")
        }
    }
}


private fun returnLocation(
    navController: NavHostController,
    location: String
) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("location", location)

    navController.popBackStack()
}