package com.fredrickjasin.freelancer_app.UI.Screens.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _error.value = null
        _successMessage.value = null
    }

    fun resetPassword() {

        val emailValue = _email.value.trim()

        if (emailValue.isEmpty()) {
            _error.value = "Email cannot be empty"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            try {

                repository.resetPassword(emailValue)

                _successMessage.value =
                    "Password reset email sent successfully"

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Failed to send reset email"

            } finally {

                _isLoading.value = false

            }
        }
    }
}