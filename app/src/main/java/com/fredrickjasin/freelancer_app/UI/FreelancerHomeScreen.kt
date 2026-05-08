package com.fredrickjasin.freelancer_app.UI

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes

@Composable
fun FreelancerHomeScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                    )
                )
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
            FreelancerHeroSection(navController)

            Column(modifier = Modifier.padding(16.dp)) {
                FreelancerStatsSection()
                
                Spacer(modifier = Modifier.height(24.dp))

                SectionHeaderWithLink("Recommended for You", "View All") {
                    navController.navigate(Routes.HomeJobPage.name)
                }
                
                // Placeholder for jobs
                Text("Browse latest opportunities tailored to your skills.", color = Color.White.copy(alpha = 0.7f))
                
                Spacer(modifier = Modifier.height(24.dp))

                SectionHeaderWithLink("Recent Notifications", "See More") {
                    navController.navigate(Routes.MessagePage.name)
                }
            }
        }
    }
}

@Composable
fun FreelancerHeroSection(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = "Find Your Next\nDream Job",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Apply to high-paying jobs globally.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(Routes.HomeJobPage.name) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Browse Jobs", color = Color(0xFF6A11CB), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun FreelancerStatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard("Earnings", "$1,200", Modifier.weight(1f))
        StatCard("Active Jobs", "3", Modifier.weight(1f))
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
