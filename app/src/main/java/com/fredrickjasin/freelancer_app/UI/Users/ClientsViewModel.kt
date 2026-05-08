package com.fredrickjasin.freelancer_app.UI.Users
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.data.Models.Clients
import com.fredrickjasin.freelancer_app.data.Repository.ClientsRepository
import com.fredrickjasin.freelancer_app.data.Repository.ClientsService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientsViewModel(
//    navController: NavHostController,
    private val repository: ClientsService = ClientsRepository()
) : ViewModel() {

    // -----------------------------------
    // STATES
    // -----------------------------------

    private val _client = MutableStateFlow(Clients())
    val client: StateFlow<Clients> = _client.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()


    // -----------------------------------
    // UPDATE FUNCTIONS
    // -----------------------------------

    fun updateUsername(value: String) {

        _client.value = _client.value.copy(
            username = value
        )
    }

    fun updateCompany(value: String) {

        _client.value = _client.value.copy(
            company = value
        )
    }

    fun updateBio(value: String) {

        _client.value = _client.value.copy(
            bio = value
        )
    }

    fun updateLocation(value: String) {

        _client.value = _client.value.copy(
            location = value
        )
    }

    fun updateProfileImage(value: String) {

        _client.value = _client.value.copy(
            profileImage = value
        )
    }


    // -----------------------------------
    // SAVE CLIENT
    // -----------------------------------

    fun saveClient() {

        val currentUid =
            FirebaseAuth.getInstance()
                .currentUser?.uid

        if (currentUid == null) {

            _error.value = "User not logged in"

            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                println("Starting client save...")

                val finalProfile =
                    _client.value.copy(
                        id = currentUid
                    )

                repository.saveClient(finalProfile)

                println("Client saved successfully!")

                _saved.value = true

            } catch (e: Exception) {

                println("SAVE ERROR: ${e.message}")

                _error.value =
                    e.message ?: "Unknown Error"

            } finally {

                _isLoading.value = false
            }
        }
    }


    // -----------------------------------
    // LOAD CLIENT
    // -----------------------------------

    fun loadClient() {

        val currentUid =
            FirebaseAuth.getInstance()
                .currentUser?.uid

        if (currentUid == null) {

            _error.value = "User not logged in"

            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                println("Loading client profile...")

                val result =
                    repository.getClient(currentUid)

                result?.let {

                    _client.value = it

                    println("Client loaded!")
                }

            } catch (e: Exception) {

                println("LOAD ERROR: ${e.message}")

                _error.value =
                    e.message ?: "Failed to load client"

            } finally {

                _isLoading.value = false
            }
        }
    }


    // -----------------------------------
    // CLEAR STATES
    // -----------------------------------

    fun clearSavedState() {

        _saved.value = false
    }

    fun clearError() {

        _error.value = null
    }
}



// -----------------------------------
// VIEWMODEL FACTORY
// -----------------------------------

class ClientsViewModelFactory(
    private val repository: ClientsService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ClientsViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return ClientsViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}