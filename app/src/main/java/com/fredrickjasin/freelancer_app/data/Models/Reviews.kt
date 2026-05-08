package com.fredrickjasin.freelancer_app.data.Models

import com.google.firebase.Timestamp

data class Reviews(
    val id: String = "",
    val freelancerId: String = "",
    val clientId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val createdAt: Timestamp? = null
)