package com.fredrickjasin.freelancer_app.UI.Screens.Jobs
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.data.Models.Job
import okhttp3.Route

@Composable
fun HomeJobsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: JobsViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.fetchJobs()
    }

    Scaffold(
        floatingActionButton = {
            if (viewModel.userRole == "client") {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.AddJobPage.name) },
                    containerColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF6A11CB)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
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
                .padding(paddingValues)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Available Jobs",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(viewModel.jobsList) { job ->
                        JobCard(job, navController, viewModel.userRole)
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(job: Job, navController: NavHostController, userRole: String?){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = job.title,
                    color = Color(0xFF6A11CB),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = Color(0xFF2575FC).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = job.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFF2575FC),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = job.description,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Budget: ${job.budget}",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "📍 ${job.location}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (userRole == "freelancer") {
                    Button(
                        onClick = {
                            navController.navigate("${Routes.ApplyJobPage.name}/${job.id}")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A11CB)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Apply Now", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Posted by: ${job.clientName}",
                color = Color.Gray,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
