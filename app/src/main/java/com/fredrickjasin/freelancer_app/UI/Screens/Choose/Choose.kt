package com.fredrickjasin.freelancer_app.UI.Screens.Choose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.theme.LogIn
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.google.firebase.auth.FirebaseAuth // ADDED THIS

@Composable
fun ChooseScreen(
    navController: NavHostController,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(pagepadding)
    ) {

        Text(
            text = "Welcome! Please Choose your Area",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card 1: Client
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        // Inside ChooseScreen.kt for the Client Card
                        onClick = {
                            val uid = FirebaseAuth.getInstance().currentUser?.uid
                            if (uid != null) {
                                // You MUST append the ID to the route name
                                navController.navigate("${Routes.ClientsPage.name}/$uid")
                            } else {
                                navController.navigate(Routes.LoginPage.name)
                            }
                        }
                    )
            ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))),
                contentAlignment = Alignment.Center
            ) {
                // ... (Keep your existing Icon/Text here)
                Text("I am a Client", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card 2: Freelancer
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        // GET THE CURRENT USER UID
                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        if (uid != null) {
                            // NAVIGATE WITH THE UID
                            navController.navigate("${Routes.FreelancersPage.name}/$uid")
                        } else {
                            // Optionally handle case where user is not logged in
                            navController.navigate(Routes.LoginPage.name)
                        }
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(listOf(Color(0xFFFC5C7D), Color(0xFF6A82FB)))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "I am a Freelancer",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Find jobs and grow your career",
                        color = Color.White.copy(0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}