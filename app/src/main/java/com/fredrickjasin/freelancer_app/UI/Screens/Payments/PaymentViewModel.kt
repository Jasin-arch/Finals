package com.fredrickjasin.freelancer_app.UI.Screens.Payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Transaction
import com.fredrickjasin.freelancer_app.data.Models.Wallet
import com.fredrickjasin.freelancer_app.data.Repository.PaymentRepository
import com.fredrickjasin.freelancer_app.data.Repository.PaymentService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repository: PaymentService = PaymentRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = auth.currentUser?.uid ?: ""

    private val _wallet = MutableStateFlow(Wallet())
    val wallet: StateFlow<Wallet> = _wallet

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadPaymentData()
    }

    fun loadPaymentData() {
        if (currentUserId.isEmpty()) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _wallet.value = repository.getWallet(currentUserId)
                _transactions.value = repository.getTransactions(currentUserId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deposit(amount: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deposit(currentUserId, amount)
                loadPaymentData()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun withdraw(amount: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.withdraw(currentUserId, amount)
                loadPaymentData()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun payFreelancer(freelancerId: String, amount: Double, jobTitle: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.processPayment(currentUserId, freelancerId, amount, "Payment for job: $jobTitle")
                loadPaymentData()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
