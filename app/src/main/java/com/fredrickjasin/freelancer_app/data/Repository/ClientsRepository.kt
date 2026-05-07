package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Implementing the service interface
class ClientsRepository : ClientsService {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Using a constant to match the ProfilesRepository style
    private val COLLECTION_NAME = "Clients"

    override suspend fun getClient(userId: String): Clients? {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            // If the document exists, convert it; otherwise return a new object with the ID
            // Matches the "default object" logic from your ProfilesRepository
            if (snapshot.exists()) {
                snapshot.toObject(Clients::class.java)
            } else {
                Clients(id = userId)
            }
        } catch (e: Exception) {
            // Log the error for debugging like in ProfilesRepository
            println("Error fetching client: ${e.message}")
            Clients(id = userId)
        }
    }

    override suspend fun saveClient(client: Clients) {
        val uid = client.id.ifBlank { auth.currentUser?.uid }
            ?: throw Exception("User must be logged in")

        try {
            val clientToSave = client.copy(id = uid)

            // 1. Save to the specific "Clients" collection
            firestore.collection(COLLECTION_NAME).document(uid).set(clientToSave).await()

            // 2. INTERACTION: Update a master "Users" collection
            // This allows the app to check Routes based on "userType"
            val userMap = mapOf(
                "id" to uid,
                "userType" to "client",
                "username" to client.username
            )
            firestore.collection("Users").document(uid).set(userMap).await()

        } catch (e: Exception) {
            println("Error saving client: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteClient(userId: String) {
        try {
            firestore.collection(COLLECTION_NAME)
                .document(userId)
                .delete()
                .await()
        } catch (e: Exception) {
            println("Error deleting client: ${e.message}")
            throw e
        }
    }
}