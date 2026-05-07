package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Reviews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewsRepository : ReviewsService {

    // In-memory storage for reviews
    private val reviews = mutableListOf<Reviews>()
    private var nextId = 1

    override suspend fun saveReview(review: Reviews): Reviews? = withContext(Dispatchers.IO) {
        // Assign an ID if not already set
        val reviewWithId = review.copy(id = nextId++)
        reviews.add(reviewWithId)
        return@withContext reviewWithId
    }

    override suspend fun getReviewsByFreelancer(freelancerId: String): List<Reviews> =
        withContext(Dispatchers.IO) {
            reviews.filter { it.freelancerId == freelancerId }
        }

    override suspend fun getAverageRating(freelancerId: String): Double = withContext(Dispatchers.IO) {
        val freelancerReviews = reviews.filter { it.freelancerId == freelancerId }
        if (freelancerReviews.isEmpty()) 0.0
        else freelancerReviews.map { it.rating.toDouble() }.average()
    }

    override suspend fun deleteReview(reviewId: Int) {
        withContext(Dispatchers.IO) {
            reviews.removeAll { it.id == reviewId }
        }
    }
}