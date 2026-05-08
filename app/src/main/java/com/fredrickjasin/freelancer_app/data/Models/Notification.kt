package com.fredrickjasin.freelancer_app.data.Models

data class Notification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
