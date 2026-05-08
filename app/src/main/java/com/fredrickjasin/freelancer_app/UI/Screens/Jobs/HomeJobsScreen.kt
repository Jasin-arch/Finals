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

            FloatingActionButton(

                onClick = { navController.navigate(Routes.HomeJobPage.name) },

                containerColor = Color(0xFF2563EB)

            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )

            }

        }

    ) { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF020617),
                            Color(0xFF0F172A)
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

                LazyColumn {

                items(
                        viewModel.jobsList
                    ) { job ->

                        JobCard(job)

                        Spacer(modifier = Modifier.height(15.dp))

                    }

                }

            }

        }

    }

}

@Composable
fun JobCard(job: Job){

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = job.title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = job.description,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Budget: ${job.budget}",
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Category: ${job.category}",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Location: ${job.location}",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Posted by: ${job.clientName}",
                color = Color.Cyan
            )

        }

    }

}