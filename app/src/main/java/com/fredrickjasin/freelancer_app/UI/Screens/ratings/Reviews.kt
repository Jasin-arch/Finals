package com.fredrickjasin.freelancer_app.UI.Screens.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ReviewsScreen(
    modifier: Modifier,
    navController: NavHostController,
    freelancerId: String,
    clientId: String,
    viewModel: ReviewsViewModel
) {
    val reviews by viewModel.reviews.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(freelancerId) {
        viewModel.loadReviews(freelancerId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Reviews & Ratings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Rating input
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) { index ->
                IconButton(onClick = { viewModel.setRating(index + 1) }) {
                    Icon(
                        imageVector = if (index < rating) Icons.Default.Star else Icons.Default.Star,
                        contentDescription = null,
                        tint = androidx.compose.ui.graphics.Color(0xFFFFD700)
                    )
                }
            }
        }

        OutlinedTextField(
            value = comment,
            onValueChange = viewModel::setComment,
            label = { Text("Write a review") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.submitReview(freelancerId, clientId) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
            else Text("Submit Review")
        }

        // Error display
        error?.let { Text(text = it, color = Color.Red) }

        Spacer(modifier = Modifier.height(16.dp))

        // Reviews list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reviews) { review ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row {
                            repeat(review.rating) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = androidx.compose.ui.graphics.Color(0xFFFFD700)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(review.comment)
                    }
                }
            }
        }
    }
}