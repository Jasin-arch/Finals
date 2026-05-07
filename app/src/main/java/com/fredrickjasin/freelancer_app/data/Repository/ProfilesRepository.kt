package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.FreelancerProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfilesRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Using a constant for the collection name prevents typos
    private val COLLECTION_NAME = "Freelancers"

    suspend fun fetchProfile(userId: String): FreelancerProfile {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            // Try to convert the document to our data class
            // If it doesn't exist, it returns a new profile with that ID
            snapshot.toObject(FreelancerProfile::class.java) ?: FreelancerProfile(id = userId)
        } catch (e: Exception) {
            // Log the error for debugging
            println("Error fetching profile: ${e.message}")
            FreelancerProfile(id = userId)
        }
    }

    suspend fun saveProfile(profile: FreelancerProfile) {
        // We use the ID already stored in the profile object
        // or fall back to the currently logged-in user's UID
        val uid = profile.id.ifBlank { auth.currentUser?.uid }
            ?: throw Exception("User must be logged in to save a profile")

        try {
            firestore.collection(COLLECTION_NAME)
                .document(uid)
                .set(profile)
                .await()
        } catch (e: Exception) {
            println("Error saving profile: ${e.message}")
            throw e
        }
    }
}