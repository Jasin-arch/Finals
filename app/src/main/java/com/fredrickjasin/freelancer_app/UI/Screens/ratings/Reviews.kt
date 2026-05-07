package com.fredrickjasin.freelancer_app.UI.Screens.ratings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ReviewsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    freelancerId: String,
    clientId: String,
    viewModel: ReviewsViewModel
) {

    val context = LocalContext.current

    val reviews by viewModel.reviews.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

    // LOAD REVIEWS
    LaunchedEffect(Unit) {
        viewModel.loadReviews(freelancerId)
    }

    // SUCCESS TOAST
    LaunchedEffect(success) {

        if (success) {

            Toast.makeText(
                context,
                "Review submitted successfully",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearState()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Text(
            text = "Reviews & Ratings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // REVIEW INPUT CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Rate Freelancer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // STAR RATING
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    repeat(5) { index ->

                        IconButton(
                            onClick = {
                                viewModel.setRating(index + 1)
                            }
                        ) {

                            Icon(
                                imageVector =
                                    if (index < rating)
                                        Icons.Filled.Star
                                    else
                                        Icons.Outlined.Star,

                                contentDescription = null,

                                tint = Color(0xFFFFD700),

                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // COMMENT INPUT
                OutlinedTextField(
                    value = comment,
                    onValueChange = {
                        viewModel.setComment(it)
                    },
                    label = {
                        Text("Write a Review")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),

                    shape = RoundedCornerShape(18.dp),

                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ERROR
                error?.let {

                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // SUBMIT BUTTON
                Button(
                    onClick = {

                        if (rating == 0) {

                            Toast.makeText(
                                context,
                                "Please select rating",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        if (comment.isBlank()) {

                            Toast.makeText(
                                context,
                                "Please enter review comment",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        viewModel.submitReview(
                            freelancerId,
                            clientId
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A11CB)
                    )
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )

                    } else {

                        Text(
                            text = "Submit Review",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "All Reviews",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (reviews.isEmpty() && !isLoading) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Text(
                    text = "No reviews yet",
                    modifier = Modifier.padding(20.dp),
                    color = Color.Gray
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(reviews) { review ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            // STARS
                            Row {

                                repeat(5) { index ->

                                    Icon(
                                        imageVector =
                                            if (index < review.rating)
                                                Icons.Filled.Star
                                            else
                                                Icons.Outlined.Star,

                                        contentDescription = null,

                                        tint = Color(0xFFFFD700),

                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = review.comment,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            HorizontalDivider()

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Client ID: ${review.clientId}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}