package com.fredrickjasin.freelancer_app.UI.Users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.fredrickjasin.freelancer_app.data.Repository.ClientsService
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

    fun updateUsername(v: String) = updateClient { copy(username = v) }
    fun updateCompany(v: String) = updateClient { copy(company = v) }
    fun updateBio(v: String) = updateClient { copy(bio = v) }
    fun updateLocation(v: String) = updateClient { copy(location = v) }
    fun updateProfileImage(v: String) = updateClient { copy(profileImage = v) }

    private fun updateClient(update: Clients.() -> Clients) {
        _client.value = _client.value.update()
    }

    fun saveClient(clients: Clients) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _saved.value = false
            try {
                repository.saveClient(_client.value)
                _saved.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _saved.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadClient() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getClient()
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