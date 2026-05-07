package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Profile
import com.fredrickjasin.freelancer_app.data.Models.Reviews
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class ReviewsService(
    private val supabase: SupabaseClient
) {

    private val tableName = "Reviews"

    // Save a new review
    suspend fun saveReview(review: Reviews): Reviews? = withContext(Dispatchers.IO) {
        val response: PostgrestResponse<Reviews> = supabase
            .from(tableName)
            .insert(review)
            .execute()

        if (response.error != null) {
            throw Exception(response.error.message)
        }
        return@withContext response.data?.firstOrNull()
    }

    // Get all reviews for a freelancer
    suspend fun getReviewsByFreelancer(freelancerId: String): List<Reviews> = withContext(Dispatchers.IO) {
        val response: PostgrestResponse<Reviews> = supabase
            .from(Profiles)
            .select("id")
            .eq("freelancer_id", freelancerId)
            .execute()

        if (response.error != null) {
            throw Exception(response.error.message)
        }
        return@withContext response.data ?: emptyList()
    }

    // Get average rating for a freelancer
    suspend fun getAverageRating(freelancerId: String): Double = withContext(Dispatchers.IO) {
        val response: PostgrestResponse<Map<String, Any>> = supabase
            .from(tableName)
            .select("rating")
            .eq("freelancer_id", freelancerId)
            .execute()

        if (response.error != null) {
            throw Exception(response.error.message)
        }

        val ratings = response.data?.mapNotNull { it["rating"] as? Number } ?: emptyList()
        return@withContext if (ratings.isEmpty()) 0.0 else ratings.map { it.toDouble() }.average()
    }

    // Optional: Delete a review
    suspend fun deleteReview(reviewId: String) = withContext(Dispatchers.IO) {
        val response = supabase
            .from(tableName)
            .delete()
            .eq("id", reviewId)
            .execute()

        if (response.error != null) {
            throw Exception(response.error.message)
        }
    }
}
