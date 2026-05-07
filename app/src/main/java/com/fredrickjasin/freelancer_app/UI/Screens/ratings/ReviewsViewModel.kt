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

    // List of reviews for a freelancer
    private val _reviews = MutableStateFlow<List<Reviews>>(emptyList())
    val reviews: StateFlow<List<Reviews>> = _reviews

    // Current rating input
    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating

    // Current comment input
    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    // Loading and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- State setters ---
    fun setRating(value: Int) {
        _rating.value = value.coerceIn(1, 5) // ensure rating is between 1 and 5
    }

    fun setComment(value: String) {
        _comment.value = value.trim()
    }

    // --- Load all reviews for a freelancer ---
    fun loadReviews(freelancerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getReviewsByFreelancer(freelancerId)
                _reviews.value = result.sortedByDescending { it.createdAt } // latest first
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load reviews"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- Submit a new review ---
    fun submitReview(freelancerId: String, clientId: String) {
        if (_rating.value == 0 || _comment.value.isEmpty()) {
            _error.value = "Please provide a rating and a comment"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val newReview = Reviews(
                    freelancerId = freelancerId,
                    clientId = clientId,
                    rating = _rating.value,
                    comment = _comment.value
                )
                repository.saveReview(newReview)

                // Refresh reviews after submitting
                loadReviews(freelancerId)

                // Clear input fields
                _rating.value = 0
                _comment.value = ""
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to submit review"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- Delete a review ---
    fun deleteReview(reviewId: Int, freelancerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.deleteReview(reviewId)
                loadReviews(freelancerId) // refresh after deletion
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete review"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- Reset error state ---
    fun clearError() {
        _error.value = null
    }
}