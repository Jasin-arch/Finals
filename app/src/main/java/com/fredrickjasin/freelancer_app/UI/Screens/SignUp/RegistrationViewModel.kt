package com.fredrickjasin.freelancer_app.UI.Screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.fredrickjasin.freelancer_app.data.Repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    // 🔥 Always keep UI-safe (non-null string)
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message
    fun registerUser(user: UserModel) {
        if (user.Email.isBlank() || user.Password.isBlank()) {
            _message.value = "Email and Password cannot be empty"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.Email).matches()) {
            _message.value = "Invalid email format"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _message.value = "Welcome"

            try {
                repository.registerUser(user)

                _message.value = "Account created successfully"

            } catch (e: Exception) {
                _message.value = e.message ?: "Registration Failed"
            }
        }
    }
}