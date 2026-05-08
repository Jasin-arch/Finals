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
        val uid = client.id.ifBlank { auth.currentUser?.uid }
            ?: throw Exception("User must be logged in")

        try {
            val clientToSave = client.copy(id = uid)

            firestore.collection(COLLECTION_NAME).document(uid).set(clientToSave).await()

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