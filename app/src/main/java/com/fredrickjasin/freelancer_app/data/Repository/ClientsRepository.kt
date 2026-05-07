package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.Clients
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage

class ClientsRepository : ClientsService {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
        supabaseKey = "YOUR_ANON_KEY_HERE"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }

    override suspend fun saveClient(client: Clients) {
        val user = supabase.auth.currentUserOrNull() ?: return

        val data = mapOf(
            "id" to user.id,
            "email" to (user.email ?: ""),
            "username" to client.username,
            "company" to client.company,
            "bio" to client.bio,
            "profile_image" to client.profileImage,
            "location" to client.location,
            "date_of_birth" to client.dateOfBirth,
            "rating" to client.rating,
            "total_reviews" to client.totalReviews
        )

        supabase
            .from("Clients")
            .upsert(data)
    }

    override suspend fun getClient(): Clients? {
        val user = supabase.auth.currentUserOrNull() ?: return null

        return supabase
            .from("Clients")
            .select {
                filter { eq("id", user.id) }
            }
            .decodeSingleOrNull<Clients>()
    }
}