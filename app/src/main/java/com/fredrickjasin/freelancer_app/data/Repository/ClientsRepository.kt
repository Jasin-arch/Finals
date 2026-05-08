package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ClientsRepository : ClientsService {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val COLLECTION_NAME = "Clients"

    override suspend fun getClient(userId: String): Clients? {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            if (snapshot.exists()) {
                snapshot.toObject(Clients::class.java)
            } else {
                Clients(id = userId)
            }
        } catch (e: Exception) {
            println("Error fetching client: ${e.message}")
            Clients(id = userId)
        }
    }

    override suspend fun saveClient(client: Clients) {
        val currentUser = auth.currentUser ?: throw Exception("Authentication Error: No user logged in")
        val uid = currentUser.uid

        try {
            val clientToSave = client.copy(id = uid)

            // 1. Save to Clients collection
            firestore.collection(COLLECTION_NAME).document(uid)
                .set(clientToSave)
                .await()

            // 2. Save to central Users collection for role management
            val userMap = mapOf(
                "id" to uid,
                "userType" to "client",
                "username" to client.username,
                "email" to (currentUser.email ?: "")
            )
            firestore.collection("Users").document(uid)
                .set(userMap)
                .await()

        } catch (e: Exception) {
            println("CLIENT SAVE FAILURE: ${e.message}")
            throw Exception("Firestore Permission Error: Make sure your Firebase Rules allow writes to 'Clients' and 'Users' collections. Original error: ${e.message}")
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