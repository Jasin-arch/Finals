package com.fredrickjasin.freelancer_app.data.Models

data class Clients(
    val id: String = "",
    val username: String = "",
    val company: String = "",
    val bio: String = "",
    val location: String = "",
    val profileImage: String = "",
    val userType: String = "client"
)