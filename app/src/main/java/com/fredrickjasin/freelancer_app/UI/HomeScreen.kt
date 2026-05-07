package com.fredrickjasin.freelancer_app.UI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.fredrickjasin.freelancer_app.UI.components.pagepadding

// Dummy user data
data class UserCard(
    val name: String,
    val profession: String,
    val image: String,
    val message: String
)

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    val freelancers = listOf(
        UserCard("Alice", "UI/UX Designer", "https://i.pravatar.cc/150?img=1", "Designing your dreams!"),
        UserCard("Bob", "Android Developer", "https://i.pravatar.cc/150?img=2", "Building sleek apps!"),
        UserCard("Charlie", "Web Developer", "https://i.pravatar.cc/150?img=3", "Your website, our code!")
    )

    val clients = listOf(
        UserCard("Delta Corp", "Tech Company", "https://i.pravatar.cc/150?img=4", "Innovate with us!"),
        UserCard("Omega Ltd", "Marketing", "https://i.pravatar.cc/150?img=5", "Boost your brand!"),
        UserCard("Sigma LLC", "Finance", "https://i.pravatar.cc/150?img=6", "Smart financial solutions!")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(pagepadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // HEADER
        Text(
            text = "Hello, Welcome!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Explore talented Freelancers and top Clients",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // DASHBOARD BUTTONS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardButton("Profile", Color(0xFF2575FC)) { /* navController.navigate(...) */ }
            DashboardButton("Settings", Color(0xFFFC5C7D)) { /* navController.navigate(...) */ }
        }

        // FREELANCERS SECTION
        SectionTitle("Top Freelancers")
        EvenDistributedCards(users = freelancers)

        // CLIENTS SECTION
        SectionTitle("Top Clients")
        EvenDistributedCards(users = clients)

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    )
}

@Composable
fun EvenDistributedCards(users: List<UserCard>) {
    val rows = users.chunked(2) // 2 cards per row

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        rows.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { user ->
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(user.image),
                                contentDescription = user.name,
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(bottom = 8.dp)
                            )
                            Text(
                                text = user.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = user.profession,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = user.message,
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (rowItems.size < 2) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DashboardButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}