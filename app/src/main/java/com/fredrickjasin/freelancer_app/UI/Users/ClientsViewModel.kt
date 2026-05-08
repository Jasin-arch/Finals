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
    private val repository: ClientsService = ClientsRepository()
) : ViewModel() {


    private val _client = MutableStateFlow(Clients())
    val client: StateFlow<Clients> = _client

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    fun updateUsername(value: String) {

        _client.value =
            _client.value.copy(username = value)

    }

    fun updateCompany(value: String) {

        _client.value =
            _client.value.copy(company = value)

    }

    fun updateBio(value: String) {

        _client.value =
            _client.value.copy(bio = value)

    }

    fun updateLocation(value: String) {

        _client.value =
            _client.value.copy(location = value)

    }

    fun updateProfileImage(value: String) {

        _client.value =
            _client.value.copy(profileImage = value)

    }


    fun saveClient(profileData: Clients) {

        val currentUid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                val finalProfile = profileData.copy(
                    id = currentUid
                )

                repository.saveClient(finalProfile)

                _saved.value = true

            } catch (e: Exception) {

                _error.value = e.message

            } finally {

                _isLoading.value = false

            }

        }

    }


    fun loadClient() {

        val currentUid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return

        viewModelScope.launch {

            _isLoading.value = true

            try {

                val result =
                    repository.getClient(currentUid)

                result?.let {

                    _client.value = it

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


class ClientsViewModelFactory(
    private val repository: ClientsService
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return ClientsViewModel(repository) as T

    }

}