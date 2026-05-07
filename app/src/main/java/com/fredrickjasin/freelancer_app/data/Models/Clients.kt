package com.fredrickjasin.freelancer_app.data.Models

import kotlinx.serialization.Serializable
@Serializable
data class Clients(
    val id: String? = null,
    val email: String? = null,
    val username: String = "",
    val company: String = "",
    val bio: String = "",
    val profileImage: String = "",
    val location: String = "",
)

