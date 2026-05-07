package com.fredrickjasin.freelancer_app.data.Models

data class Reviews(
    val id: Int? = null,
    val freelancerId: String = "",
    val clientId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val createdAt: String = ""

)
