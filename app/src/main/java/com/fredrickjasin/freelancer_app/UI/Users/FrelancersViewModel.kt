package com.fredrickjasin.freelancer_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Profile
import com.fredrickjasin.freelancer_app.data.Repository.ProfileService
//import com.fredrickjasin.freelancer_app.data.repository.ProfileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileService
) : ViewModel() {

    // 📦 UI State
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ✏️ Field updates
    fun updateUsername(value: String) {
        _profile.value = _profile.value.copy(username = value)
    }

    fun updateProfession(value: String) {
        _profile.value = _profile.value.copy(profession = value)
    }

    fun updateBio(value: String) {
        _profile.value = _profile.value.copy(bio = value)
    }

    fun updateDOB(value: String) {
        _profile.value = _profile.value.copy(dateOfBirth = value)
    }

    fun updateLocation(value: String) {
        _profile.value = _profile.value.copy(location = value)
    }

    fun updateProfileImage(value: String) {
        _profile.value = _profile.value.copy(profileImage = value)
    }

    // ☁️ Save profile
    fun saveProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                repository.saveProfile(_profile.value)

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 📥 Load profile
    fun loadProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val result = repository.getProfile()
                result?.let {
                    _profile.value = it
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}