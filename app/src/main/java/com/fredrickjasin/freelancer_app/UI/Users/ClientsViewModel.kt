package com.fredrickjasin.freelancer_app.UI.Users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository
import com.fredrickjasin.freelancer_app.data.Repository.ClientsService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientsViewModel(
    private val repository: ClientsService
) : ViewModel() {

    private val _client = MutableStateFlow(Clients())
    val client: StateFlow<Clients> = _client

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    // --- UI UPDATES ---
    fun updateUsername(v: String) = updateClient { copy(username = v) }
    fun updateCompany(v: String) = updateClient { copy(company = v) }
    fun updateBio(v: String) = updateClient { copy(bio = v) }
    fun updateLocation(v: String) = updateClient { copy(location = v) }
    fun updateProfileImage(v: String) = updateClient { copy(profileImage = v) }

    private fun updateClient(update: Clients.() -> Clients) {
        _client.value = _client.value.update()
    }

    // --- DATABASE OPERATIONS ---

    /**
     * Saves the current client state to Firebase.
     * We ignore the parameter passed from UI and use the internal state for consistency.
     */
    fun saveClient(profileData: Clients) {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Merge the UI input with the ID
                val finalProfile = profileData.copy(id = currentUid)
                repository.saveClient(finalProfile)
                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    /**
     * Loads the client profile using the current authenticated UID.
     */
    fun loadClient() {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // Matches the new interface signature: getClient(userId: String)
                val result = repository.getClient(currentUid)
                if (result != null) {
                    _client.value = result
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
class FreelancersViewModelFactory(
    private val repository: ClientsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClientsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}