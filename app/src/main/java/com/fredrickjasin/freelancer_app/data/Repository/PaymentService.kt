package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.Transaction
import com.fredrickjasin.freelancer_app.data.Models.Wallet

interface PaymentService {
    suspend fun getWallet(userId: String): Wallet
    suspend fun getTransactions(userId: String): List<Transaction>
    suspend fun processPayment(fromUserId: String, toUserId: String, amount: Double, description: String)
    suspend fun deposit(userId: String, amount: Double)
    suspend fun withdraw(userId: String, amount: Double)
}
