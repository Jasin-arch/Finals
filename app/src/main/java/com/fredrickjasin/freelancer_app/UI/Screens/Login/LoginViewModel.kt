package com.fredrickjasin.freelancer_app.UI.Screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.fredrickjasin.freelancer_app.data.Repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Individual states instead of a data class
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _error.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _error.value = null
    }

    fun loginUser() {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value.trim()

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _error.value = "Email and Password cannot be empty"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _success.value = false

            try {
                val user = UserModel(
                    Email = emailValue,
                    Password = passwordValue
                )

                repository.loginUser(user)

                _success.value = true

            } catch (e: Exception) {
                _error.value = e.message ?: "Login Failed"
            } finally {
                _isLoading.value = false
            }
        }
    }
}