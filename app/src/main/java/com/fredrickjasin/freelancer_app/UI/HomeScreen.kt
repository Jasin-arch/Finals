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
            // ---------------- HERO SECTION ----------------
            HeroSection()

            Column(modifier = Modifier.padding(16.dp)) {
                // ---------------- QUICK ACTIONS ----------------
                QuickActions(navController)

                Spacer(modifier = Modifier.height(24.dp))

                // ---------------- AI LEARNING HUB (AIVOSO style) ----------------
                SectionHeaderWithLink("AI Learning Hub", "View All")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(courseList) { course ->
                        CourseCard(course)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ---------------- MARKETPLACE ----------------
                SectionHeaderWithLink("Marketplace", "Explore")
                MarketplaceSection()

                Spacer(modifier = Modifier.height(24.dp))

                // ---------------- RECENT JOBS ----------------
                SectionHeaderWithLink("Trending Jobs", "More")
                jobList.take(3).forEach { job ->
                    JobListItem(job)
                }
            }
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
                text = "Unlock your potential\nwith AI-powered learning",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Personalized technology education for the future.",
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
fun QuickActions(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionItem(Icons.Default.School, "Courses") { }
        ActionItem(Icons.Default.Work, "Jobs") { }
        ActionItem(Icons.Default.Storefront, "Market") { }
        ActionItem(Icons.Default.AccountBalanceWallet, "Wallet") { }
    }
}

@Composable
fun ActionItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White.copy(alpha = 0.2f),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = Color.White, fontSize = 12.sp)
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
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(course.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = course.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                Text(text = course.instructor, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = course.price, fontWeight = FontWeight.Bold, color = Both, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun MarketplaceSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("AI Assistant Marketplace", fontWeight = FontWeight.Bold, color = Color.White)
                Text("Pre-trained bots for your business", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun JobListItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Both.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Terminal, contentDescription = null, tint = Both)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(job.budget, color = Color.Gray, fontSize = 13.sp)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
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
            selected = false,
            onClick = { navController.navigate(Routes.DashboardPage.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate(Routes.SettingsPage.name) }
        )
    }
}

// Data Models
data class Course(val title: String, val instructor: String, val price: String, val imageUrl: String)
data class Job(val title: String, val budget: String)

val courseList = listOf(
    Course("AI Fundamentals", "Dr. AI", "Free", "https://images.unsplash.com/photo-1677442136019-21780ecad995"),
    Course("Kotlin for AI", "Jane Doe", "$49", "https://images.unsplash.com/photo-1516116216624-53e697fedbea"),
    Course("Mastering Prompts", "John Smith", "$29", "https://images.unsplash.com/photo-1620712943543-bcc4628c9456")
)

val jobList = listOf(
    Job("Build a Chatbot", "$500 - $1000"),
    Job("Fine-tune Llama 3", "$2000"),
    Job("Data Labeling Project", "$200")
)
