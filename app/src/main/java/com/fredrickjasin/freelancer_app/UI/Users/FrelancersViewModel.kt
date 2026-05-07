package com.fredrickjasin.freelancer_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.FreelancerProfile
import com.fredrickjasin.freelancer_app.data.Repository.ProfilesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FreelancersViewModel(
    private val repository: ProfilesRepository = ProfilesRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _profile = MutableStateFlow(FreelancerProfile())
    val profile: StateFlow<FreelancerProfile> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    fun loadProfile(userId: String? = null) {
        // Use provided ID or fallback to current logged-in user
        val id = userId ?: auth.currentUser?.uid ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedProfile = repository.fetchProfile(id)
                // If profile exists in Firebase, update our state
                fetchedProfile?.let { _profile.value = it }
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveProfile() {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUid == null) {
            _error.value = "You must be logged in to save."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Use 'id' here because that's the name in your Data Class
                val profileToSave = _profile.value.copy(id = currentUid)

                repository.saveProfile(profileToSave)
                _saved.value = true
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

    // ---------------- FIELD UPDATES ----------------
    fun updateUsername(value: String) = updateState { it.copy(username = value) }
    fun updateProfession(value: String) = updateState { it.copy(profession = value) }
    fun updateBio(value: String) = updateState { it.copy(bio = value) }
    fun updateDOB(value: String) = updateState { it.copy(dateOfBirth = value) }
    fun updateLocation(value: String) = updateState { it.copy(location = value) }

    // Helper to reduce boilerplate
    private fun updateState(update: (FreelancerProfile) -> FreelancerProfile) {
        _profile.value = update(_profile.value)
    }
}