package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.FreelancerProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FreelancersRepository : FreelancerService {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val COLLECTION_NAME = "Freelancers"

    override suspend fun fetchProfile(userId: String): FreelancerProfile {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            snapshot.toObject(FreelancerProfile::class.java) ?: FreelancerProfile(id = userId)
        } catch (e: Exception) {
            println("Error fetching profile: ${e.message}")
            FreelancerProfile(id = userId) // Returns a default empty profile with the ID if fetching fails
        }
    }

    override suspend fun saveProfile(profile: FreelancerProfile) {
        val currentUser = auth.currentUser ?: throw Exception("Authentication Error: No user logged in")
        val uid = currentUser.uid

        try {
            val profileToSave = profile.copy(id = uid)
            
            // 1. Save to Freelancers collection
            firestore.collection(COLLECTION_NAME)
                .document(uid)
                .set(profileToSave)
                .await()

            // 2. Update central Users collection
            val userMap = mapOf(
                "id" to uid,
                "userType" to "freelancer",
                "username" to profile.username,
                "email" to (currentUser.email ?: "")
            )
            firestore.collection("Users").document(uid)
                .set(userMap)
                .await()

        } catch (e: Exception) {
            println("FREELANCER SAVE FAILURE: ${e.message}")
            throw Exception("Firestore Permission Error: Make sure your Firebase Rules allow writes to 'Freelancers' and 'Users' collections. Original error: ${e.message}")
        }
    }
}
