package com.fredrickjasin.freelancer_app.data.Models

data class Profile(
    val username: String = "",
    val profession: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val rating: Int = 0,
    val totalReviews: Int = 0,
    val location: String = "",
    val dateOfBirth: String = ""
)


