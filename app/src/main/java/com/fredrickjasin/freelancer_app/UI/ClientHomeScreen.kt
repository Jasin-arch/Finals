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
fun ClientHomeScreen(
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
            ClientHeroSection(navController)

            Column(modifier = Modifier.padding(16.dp)) {
                ClientQuickActions(navController)
                
                Spacer(modifier = Modifier.height(24.dp))

                SectionHeaderWithLink("Your Active Jobs", "Manage") {
                    navController.navigate(Routes.DashboardPage.name)
                }
                
                Text("Track progress and communicate with hires.", color = Color.White.copy(alpha = 0.7f))

                Spacer(modifier = Modifier.height(24.dp))

                // Success Stories Section for Clients
                SectionHeaderWithLink("Client Success Stories", "")
                Text("How businesses scale with WorkBridge.", color = Color.White.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.height(16.dp))
                ClientSuccessStories()

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeaderWithLink("Top Rated Freelancers", "Explore") {
                    navController.navigate(Routes.FreelancerListPage.name)
                }
            }
        }
    }
}

@Composable
fun ClientHeroSection(navController: NavHostController) {
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
                text = "Hire the Best\nTalent Today",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Post a job and find your perfect match.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(Routes.AddJobPage.name) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Post a Job", color = Color(0xFF2575FC), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ClientQuickActions(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuickActionCard("Post Job", Icons.Default.Add, Modifier.weight(1f)) {
            navController.navigate(Routes.AddJobPage.name)
        }
        QuickActionCard("Find Talent", Icons.Default.Search, Modifier.weight(1f)) {
            navController.navigate(Routes.FreelancerListPage.name)
        }
    }
}

@Composable
fun QuickActionCard(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ClientSuccessStories() {
    val stories = listOf(
        "Website Overhaul" to "Found an amazing developer who transformed our outdated site into a modern masterpiece. - Sarah, CEO",
        "Branding Success" to "The designers here delivered a brand identity that truly resonates with our audience. - Mark, Founder",
        "Mobile App Launch" to "From concept to App Store, the freelancers here made our vision a reality. - Tech Innovations",
        "Marketing Growth" to "Our social media presence exploded thanks to the expert strategists we hired. - Local Boutique",
        "Quick Turnaround" to "Needed a report analyzed overnight, and the expert delivered ahead of schedule. - Project Manager"
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(stories) { story ->
            Card(
                modifier = Modifier
                    .width(260.dp)
                    .height(140.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(story.first, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(story.second, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, lineHeight = 20.sp)
                }
            }
        }
    }
}
