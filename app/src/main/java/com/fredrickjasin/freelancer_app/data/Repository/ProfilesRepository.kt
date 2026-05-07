package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.Profile
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class ProfileRepository : ProfileService {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
        supabaseKey = "sb_publishable_qn2rUEsgR7CAaQFNl1uBYQ_oVxQwGxV"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }

    override suspend fun saveProfile(profile: Profile) {
        val user = supabase.auth.currentUserOrNull() ?: return

        val data = mapOf(
            "id" to user.id,
            "email" to user.email,
            "username" to profile.username,
            "profession" to profile.profession,
            "bio" to profile.bio,
            "profile_image" to profile.profileImage,
            "location" to profile.location,
            "date_of_birth" to profile.dateOfBirth,
            "rating" to profile.rating,
            "total_reviews" to profile.totalReviews
        )

        supabase.from("profiles").upsert(data)
    }

    override suspend fun getProfile(): Profile? {
        val user = supabase.auth.currentUserOrNull() ?: return null

        return supabase
            .from("profiles")
            .select {
                filter {
                    eq("id", user.id)
                }
            }
            .decodeSingle<Profile>()
    }
}