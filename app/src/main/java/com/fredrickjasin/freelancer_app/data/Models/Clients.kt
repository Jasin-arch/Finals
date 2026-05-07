package com.fredrickjasin.freelancer_app.data.Models

import kotlinx.serialization.Serializable

data class Clients(
@Serializable
    val id: String? = null,
    val email: String? = null,
    val username: String = "",
    val company: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val location: String = "",
    val dateOfBirth: String = "",
    val rating: Double = 0.0,
    val totalReviews: Int = 0
)

