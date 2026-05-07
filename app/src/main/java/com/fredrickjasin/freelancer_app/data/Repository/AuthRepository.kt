package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository : AuthService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun registerUser(userDetails: UserModel) {

        auth.createUserWithEmailAndPassword(
            userDetails.Email,
            userDetails.Password
        ).await()

    }

    override suspend fun loginUser(userDetails: UserModel) {

        auth.signInWithEmailAndPassword(
            userDetails.Email,
            userDetails.Password
        ).await()

    }

    override suspend fun resetPassword(email: String) {

        auth.sendPasswordResetEmail(email).await()

    }

    override suspend fun getUserProfile(user: UserModel) {
        // Optional later
    }

    override suspend fun logoutUser() {

        auth.signOut()

    }
}