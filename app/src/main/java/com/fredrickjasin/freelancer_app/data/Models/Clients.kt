package com.fredrickjasin.freelancer_app.data.Models

data class Clients(
    val id: String = "",           // This will match the Firebase Auth UID
    val username: String = "",
    val company: String = "",
    val bio: String = "",
    val location: String = "",
    val profileImage: String = "",
    val userType: String = "client" // Helps distinguish between users in the DB
)