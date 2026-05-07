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
        supabaseKey = "sb_publishable_qn2rUEsgR7CAaQFNl1uBYQ_oVxQwGxV"
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
//            "rating" to profile.rating,
//            "total_reviews" to profile.totalReviews
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