package com.fredrickjasin.freelancer_app.UI.Screens.ratings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Reviews
import com.fredrickjasin.freelancer_app.data.Repository.ReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel(
    private val repository: ReviewsRepository
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Reviews>>(emptyList())
    val reviews: StateFlow<List<Reviews>> = _reviews

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun setRating(r: Int) { _rating.value = r }
    fun setComment(c: String) { _comment.value = c }

    fun loadReviews(freelancerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _reviews.value = repository.getReviews(freelancerId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitReview(freelancerId: String, clientId: String) {
        if (_rating.value == 0 || _comment.value.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.submitReview(
                    Reviews(
                        freelancerId = freelancerId,
                        clientId = clientId,
                        rating = _rating.value,
                        comment = _comment.value
                    )
                )
                // refresh reviews
                loadReviews(freelancerId)
                _comment.value = ""
                _rating.value = 0
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}