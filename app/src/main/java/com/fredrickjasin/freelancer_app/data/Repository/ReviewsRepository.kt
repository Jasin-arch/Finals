package com.fredrickjasin.freelancer_app.data.Repository


import com.fredrickjasin.freelancer_app.data.Models.Reviews
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class ReviewsRepository {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
        supabaseKey = "sb_publishable_qn2rUEsgR7CAaQFNl1uBYQ_oVxQwGxV"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }

    suspend fun getReviews(freelancerId: String): List<Reviews> {
        return supabase
            .from("Reviews")
            .select { filter { eq("freelancerId", freelancerId) } }
            .decodeList<Reviews>()
    }

    suspend fun submitReview(review: Reviews) {
        supabase.from("Reviews").insert(review)
    }
}