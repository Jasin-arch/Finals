package com.fredrickjasin.freelancer_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Profile
import com.fredrickjasin.freelancer_app.data.Repository.ProfileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FreelancersViewModel(
    private val repository: ProfileService
) : ViewModel() {

    // Current profile state
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Save status
    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    // -------------------------
    // Update functions for fields
    // -------------------------
    fun updateUsername(v: String) {
        _profile.value = _profile.value.copy(username = v)
    }

    fun updateProfession(v: String) {
        _profile.value = _profile.value.copy(profession = v)
    }

    fun updateBio(v: String) {
        _profile.value = _profile.value.copy(bio = v)
    }

    fun updateDOB(v: String) {
        _profile.value = _profile.value.copy(dateOfBirth = v)
    }

    fun updateLocation(v: String) {
        _profile.value = _profile.value.copy(location = v)
    }

    fun updateProfileImage(v: String) {
        _profile.value = _profile.value.copy(profileImage = v)
    }


    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _saved.value = false

            try {
                repository.saveProfile(_profile.value)
                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _saved.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getProfile()
                if (result != null) {
                    _profile.value = result
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun clearSavedState() {
        _saved.value = false
    }
}