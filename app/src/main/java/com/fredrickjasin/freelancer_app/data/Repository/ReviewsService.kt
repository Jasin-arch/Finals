package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Reviews

interface ReviewsService {


    /**
     * Save a new review
     * @param review The review to save
     * @return The saved review with ID if successful
     */
    suspend fun saveReview(review: Reviews): Reviews?

    /**
     * Get all reviews for a specific freelancer
     * @param freelancerId The ID of the freelancer
     * @return List of reviews for the freelancer
     */
    suspend fun getReviewsByFreelancer(freelancerId: String): List<Reviews>

    /**
     * Calculate the average rating for a freelancer
     * @param freelancerId The ID of the freelancer
     * @return Average rating as Double
     */
    suspend fun getAverageRating(freelancerId: String): Double

    /**
     * Delete a review by its ID
     * @param reviewId The ID of the review to delete
     */
    suspend fun deleteReview(reviewId: Int)
}