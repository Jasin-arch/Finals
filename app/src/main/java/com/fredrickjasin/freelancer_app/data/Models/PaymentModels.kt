package com.fredrickjasin.freelancer_app.data.Models
import com.google.firebase.Timestamp

data class Wallet(
    val userId: String = "",
    val balance: Double = 0.0
)

data class Transaction(
    val id: String = "",
    val fromUserId: String = "",
    val toUserId: String = "",
    val amount: Double = 0.0,
    val type: String = "deposit", // deposit, withdrawal, payment
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
