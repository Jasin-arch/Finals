package com.fredrickjasin.freelancer_app.UI.Screens.SignUp

import com.fredrickjasin.freelancer_app.data.Models.UserModel
import com.fredrickjasin.freelancer_app.data.Repository.AuthRepository
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.DriverManager.println


sealed class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class RegistrationViewModel : ViewModel() {

    val authRepository = AuthRepository()

    //     state
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()


    //     methods
    fun registerUser(userModel: UserModel) {
        _isLoading.value = true
        viewModelScope.launch {

            try {
                authRepository.registerUser(userModel)
                _isLoading.value =false
                _message.value="success!"
            }catch (e:Error){
                _isLoading.value =false
                _message.value="Oops! Something went wrong:${e.message}"
            }

        }
    }
}