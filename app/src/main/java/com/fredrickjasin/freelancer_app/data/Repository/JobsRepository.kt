package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class JobsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val auth = FirebaseAuth.getInstance()

    private val COLLECTION_NAME = "Jobs"


    suspend fun addJob(job: Job) {

        val currentUser =
            auth.currentUser?.uid
                ?: throw Exception("User not logged in")

        val document =
            firestore.collection(COLLECTION_NAME)
                .document()

        val newJob = job.copy(
            id = document.id,
            clientId = currentUser
        )

        try {

            document
                .set(newJob)
                .await()

        }catch (e:Exception){

            println("Error adding job ${e.message}")

            throw e

        }

    }

    suspend fun fetchJobs(): List<Job> {

        return try {

            val snapshot =
                firestore.collection(COLLECTION_NAME)
                    .get()
                    .await()

            snapshot.documents.mapNotNull {

                it.toObject(Job::class.java)

            }

        }catch (e:Exception){

            println("Error fetching jobs ${e.message}")

            emptyList()

        }

    }

}