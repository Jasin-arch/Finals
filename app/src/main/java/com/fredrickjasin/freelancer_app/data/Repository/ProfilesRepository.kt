package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.Profile
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage

class ProfilesRepository : ProfileService {

    // ⚠️ Better: move this to a singleton later
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd5c21sZXB0cGNhaGh4b3ZpcXBvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY5ODM2MDgsImV4cCI6MjA5MjU1OTYwOH0.cgII9h3sr1r-I4E9kX3o__WDvuttdbsj5SX00hA04KI"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }

    override suspend fun saveProfile(profile: Profile) {

        val user = supabase.auth.currentUserOrNull()
            ?: throw Exception("User not logged in")

        val data = mapOf(
            "id" to user.id,
            "email" to (user.email ?: ""),
            "username" to profile.username,
            "profession" to profile.profession,
            "bio" to profile.bio,
            "profile_image" to profile.profileImage,
            "location" to profile.location,
            "date_of_birth" to profile.dateOfBirth,
            "rating" to profile.rating,
            "total_reviews" to profile.totalReviews
        )

        try {
            supabase
                .from("Profiles")
                .upsert(data)
        } catch (e: Exception) {
            throw Exception("Failed to save profile: ${e.message}")
        }
    }

    override suspend fun getProfile(): Profile? {

        val user = supabase.auth.currentUserOrNull()
            ?: return null

        return try {
            supabase
                .from("Profiles")
                .select {
                    filter {
                        eq("id", user.id)
                    }
                }
                .decodeSingleOrNull<Profile>()
        } catch (e: Exception) {
            null
        }
    }
}