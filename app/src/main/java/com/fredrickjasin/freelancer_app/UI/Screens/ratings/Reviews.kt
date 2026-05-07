package com.fredrickjasin.freelancer_app.UI.Screens.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.data.Models.Reviews

@Composable
fun ReviewsScreen(
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Reviews & Ratings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // --- Star rating input ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(5) { index ->
                IconButton(onClick = { viewModel.setRating(index + 1) }) {
                    Icon(
                        imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700)
                    )
                }
            }
        }

        // --- Comment input ---
        OutlinedTextField(
            value = comment,
            onValueChange = viewModel::setComment,
            label = { Text("Write a review") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // --- Submit button ---
        Button(
            onClick = { viewModel.submitReview(freelancerId, clientId) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Submit Review")
            }
        }

        // --- Error message ---
        error?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp)) }

        // --- Reviews list ---
        if (reviews.isEmpty() && !isLoading) {
            Text("No reviews yet.", color = Color.Gray, modifier = Modifier.padding(top = 16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reviews) { review ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(16.dp)
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
}