package com.fredrickjasin.freelancer_app.data.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getCurrentUserRole(): String? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val doc = firestore.collection("Users").document(uid).get().await()
            doc.getString("userType")
        } catch (e: Exception) {
            null
        }
    }
}
