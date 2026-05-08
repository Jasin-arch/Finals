package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Reviews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ReviewsRepository : ReviewsService {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("reviews")

    override suspend fun saveReview(review: Reviews): Reviews? {
        return try {

            val docRef = collection.document()

            val data = review.copy(
                id = docRef.id
            )

            docRef.set(data).await()

            data

        } catch (e: Exception) {
            throw Exception("Failed to save review: ${e.message}")
        }
    }

    override suspend fun getReviewsByFreelancer(
        freelancerId: String
    ): List<Reviews> {

        return try {

            val snapshot = collection
                .whereEqualTo("freelancerId", freelancerId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull {

                it.toObject(Reviews::class.java)
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getAverageRating(
        freelancerId: String
    ): Double {

        return try {

            val reviews = getReviewsByFreelancer(freelancerId)

            if (reviews.isEmpty()) return 0.0

            reviews.map { it.rating }
                .average()

        } catch (e: Exception) {
            0.0
        }
    }

    override suspend fun deleteReview(
        reviewId: String
    ) {

        try {

            collection
                .document(reviewId)
                .delete()
                .await()

        } catch (e: Exception) {
            throw Exception("Failed to delete review: ${e.message}")
        }
    }
}