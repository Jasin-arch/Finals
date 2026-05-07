package com.fredrickjasin.freelancer_app.viewmodel

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

    fun updateUsername(v: String) {
        _client.value = _client.value.copy(username = v)
    }

    fun updateCompany(v: String) {
        _client.value = _client.value.copy(company = v)
    }

    fun updateBio(v: String) {
        _client.value = _client.value.copy(bio = v)
    }

    fun updateDOB(v: String) {
        _client.value = _client.value.copy(dateOfBirth = v)
    }

    fun updateLocation(v: String) {
        _client.value = _client.value.copy(location = v)
    }

    fun updateProfileImage(v: String) {
        _client.value = _client.value.copy(profileImage = v)
    }

    fun saveClient() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _saved.value = false

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
            try {
                _isLoading.value = true
                _error.value = null

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