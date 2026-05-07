package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Reviews

interface ReviewsService {

    suspend fun saveReview(review: Reviews): Reviews?

    suspend fun getReviewsByFreelancer(freelancerId: String): List<Reviews>

    suspend fun getAverageRating(freelancerId: String): Double

    suspend fun deleteReview(reviewId: String)
}