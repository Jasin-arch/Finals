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
    navController: NavHostController
) {
    var userRole by remember { mutableStateOf<String?>(null) }
    val userRepository = remember { com.fredrickjasin.freelancer_app.data.Repository.UserRepository() }

    LaunchedEffect(Unit) {
        userRole = userRepository.getCurrentUserRole()
    }

    if (userRole == "client") {
        ClientHomeScreen(modifier, navController)
    } else {
        // Defaults to freelancer if role is null or freelancer
        FreelancerHomeScreen(modifier, navController)
    }
}

@Composable
fun SectionHeaderWithLink(title: String, linkText: String, onClick: () -> Unit = {}) {
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
            modifier = Modifier.clickable { onClick() }
        )
    }
}




@Composable
fun BottomNavBar(navController: NavHostController) {
    var userRole by remember { mutableStateOf<String?>(null) }
    val userRepository = remember { com.fredrickjasin.freelancer_app.data.Repository.UserRepository() }

    LaunchedEffect(Unit) {
        userRole = userRepository.getCurrentUserRole()
    }

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
            icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = null) },
            label = { Text("Wallet") },
            selected = false,
            onClick = { navController.navigate(Routes.PaymentPage.name) }
        )

        if (userRole == "client") {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                label = { Text("Post Job") },
                selected = false,
                onClick = { navController.navigate(Routes.AddJobPage.name) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Search, contentDescription = null) },
                label = { Text("Talent") },
                selected = false,
                onClick = { navController.navigate(Routes.FreelancerListPage.name) }
            )
        } else {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Work, contentDescription = null) },
                label = { Text("Jobs") },
                selected = false,
                onClick = { navController.navigate(Routes.HomeJobPage.name) }
            )
        }

        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = { Text("Alerts") },
            selected = false,
            onClick = { navController.navigate(Routes.MessagePage.name) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate(Routes.SettingsPage.name) }
        )
    }
}


