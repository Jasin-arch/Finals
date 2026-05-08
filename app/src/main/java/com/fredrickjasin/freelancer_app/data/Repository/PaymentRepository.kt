package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Transaction
import com.fredrickjasin.freelancer_app.data.Models.Wallet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PaymentRepository : PaymentService {
    private val firestore = FirebaseFirestore.getInstance()
    private val WALLETS = "Wallets"
    private val TRANSACTIONS = "Transactions"

    override suspend fun getWallet(userId: String): Wallet {
        val doc = firestore.collection(WALLETS).document(userId).get().await()
        return doc.toObject(Wallet::class.java) ?: Wallet(userId, 0.0)
    }

    override suspend fun getTransactions(userId: String): List<Transaction> {
        // Transactions where user is sender OR receiver
        val sent = firestore.collection(TRANSACTIONS)
            .whereEqualTo("fromUserId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().await()
        
        val received = firestore.collection(TRANSACTIONS)
            .whereEqualTo("toUserId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().await()
            
        val list = (sent.toObjects(Transaction::class.java) + received.toObjects(Transaction::class.java))
        return list.sortedByDescending { it.timestamp }
    }

    override suspend fun processPayment(fromUserId: String, toUserId: String, amount: Double, description: String) {
        try {
            firestore.runTransaction { transaction ->
                val fromWalletRef = firestore.collection(WALLETS).document(fromUserId)
                val toWalletRef = firestore.collection(WALLETS).document(toUserId)
                
                val fromSnap = transaction.get(fromWalletRef)
                val fromWallet = fromSnap.toObject(Wallet::class.java) ?: Wallet(fromUserId, 0.0)
                
                if (fromWallet.balance < amount) {
                    throw Exception("Insufficient balance")
                }
                
                val toSnap = transaction.get(toWalletRef)
                val toWallet = toSnap.toObject(Wallet::class.java) ?: Wallet(toUserId, 0.0)
                
                transaction.set(fromWalletRef, fromWallet.copy(balance = fromWallet.balance - amount))
                transaction.set(toWalletRef, toWallet.copy(balance = toWallet.balance + amount))
                
                val transRef = firestore.collection(TRANSACTIONS).document()
                val newTrans = Transaction(
                    id = transRef.id,
                    fromUserId = fromUserId,
                    toUserId = toUserId,
                    amount = amount,
                    type = "payment",
                    description = description
                )
                transaction.set(transRef, newTrans)
            }.await()
        } catch (e: Exception) {
            throw Exception("Firestore Permission Error: Make sure your Firebase Rules allow Transactions and writes to 'Wallets' and 'Transactions' collections. Original error: ${e.message}")
        }
    }

    override suspend fun deposit(userId: String, amount: Double) {
        firestore.runTransaction { transaction ->
            val walletRef = firestore.collection(WALLETS).document(userId)
            val snap = transaction.get(walletRef)
            val wallet = snap.toObject(Wallet::class.java) ?: Wallet(userId, 0.0)
            
            transaction.set(walletRef, wallet.copy(balance = wallet.balance + amount))
            
            val transRef = firestore.collection(TRANSACTIONS).document()
            val newTrans = Transaction(
                id = transRef.id,
                toUserId = userId,
                amount = amount,
                type = "deposit",
                description = "Deposit to wallet"
            )
            transaction.set(transRef, newTrans)
        }.await()
    }

    override suspend fun withdraw(userId: String, amount: Double) {
        firestore.runTransaction { transaction ->
            val walletRef = firestore.collection(WALLETS).document(userId)
            val snap = transaction.get(walletRef)
            val wallet = snap.toObject(Wallet::class.java) ?: Wallet(userId, 0.0)
            
            if (wallet.balance < amount) throw Exception("Insufficient balance")
            
            transaction.set(walletRef, wallet.copy(balance = wallet.balance - amount))
            
            val transRef = firestore.collection(TRANSACTIONS).document()
            val newTrans = Transaction(
                id = transRef.id,
                fromUserId = userId,
                amount = amount,
                type = "withdrawal",
                description = "Withdrawal from wallet"
            )
            transaction.set(transRef, newTrans)
        }.await()
    }
}
