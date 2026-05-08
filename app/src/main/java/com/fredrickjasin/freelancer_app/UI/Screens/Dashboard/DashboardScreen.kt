package com.fredrickjasin.freelancer_app.UI.Screens.Dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.UI.Screens.Payments.PaymentViewModel
import com.fredrickjasin.freelancer_app.data.Repository.UserRepository

@Composable
fun DashboardScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    var userRole by remember { mutableStateOf<String?>(null) }
    val userRepository = remember { UserRepository() }

    LaunchedEffect(Unit) {
        userRole = userRepository.getCurrentUserRole()
    }

    Scaffold(
        bottomBar = { com.fredrickjasin.freelancer_app.UI.BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = "My Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (userRole == "client") {
                    Button(
                        onClick = { navController.navigate(Routes.AddJobPage.name) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Post a Job", color = Color(0xFF6A11CB), fontWeight = FontWeight.Bold)
                    }
                }
                
                Button(
                    onClick = { navController.navigate(Routes.PaymentPage.name) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Wallet", color = Color(0xFF6A11CB), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (userRole == "client") "Active Contracts" else "Ongoing Projects",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Enhanced Job Card for Dashboard
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Website Redesign", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF6A11CB))
                        Surface(
                            color = Color(0xFF4CAF50).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Active",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = Color(0xFF4CAF50),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if (userRole == "client") "Freelancer: John Doe" else "Client: ACME Corp", color = Color.Gray, fontSize = 14.sp)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Budget", color = Color.Gray, fontSize = 12.sp)
                            Text("$500.00", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF2E7D32))
                        }
                        
                        if (userRole == "client") {
                            Button(
                                onClick = { 
                                    paymentViewModel.payFreelancer("mock_freelancer_id", 500.0, "Website Redesign")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Pay Now", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { },
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFF6A11CB))
                            ) {
                                Text("Submit Milestone", color = Color(0xFF6A11CB))
                            }
                        }
                    }
                }
            }
        }
    }
}
