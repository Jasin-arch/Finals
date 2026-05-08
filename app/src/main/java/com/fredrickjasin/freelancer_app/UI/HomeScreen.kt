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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.UI.theme.Both

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    userRole: String = "freelancer"
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
            HeroSection()

//            Column(modifier = Modifier.padding(16.dp)) {
//                QuickActions(navController)
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                SectionHeaderWithLink("AI Learning Hub", "View All")
//                LazyRow(
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(vertical = 8.dp)
//                ) {
//                    items(courseList) { course ->
//                        CourseCard(course)
//                    }
//                }

                Spacer(modifier = Modifier.height(24.dp))

//                SectionHeaderWithLink("Marketplace", "Explore")
//                MarketplaceSection()

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeaderWithLink("Trending Jobs", "More")
            }
        }
    }


@Composable
fun HeroSection() {
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
                text = "Welcome to your home page ",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Get Jobs and Add Jobs ",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Get Started", color = Both, fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Composable
fun SectionHeaderWithLink(title: String, linkText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(
            text = linkText,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.clickable { }
        )
    }
}




@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Both
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = { navController.navigate(Routes.HomePage.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") },
            selected = true,
            onClick = { navController.navigate(Routes.DashboardPage.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = true,
            onClick = { navController.navigate(Routes.SettingsPage.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Work, contentDescription = null) },
            label = { Text("App Jobs") },
            selected = true,
            onClick = { navController.navigate(Routes.HomeJobPage.name) }
        )
    }
}


