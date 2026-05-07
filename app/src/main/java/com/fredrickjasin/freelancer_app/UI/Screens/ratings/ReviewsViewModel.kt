package com.fredrickjasin.freelancer_app.UI.Screens.ratings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Reviews
import com.fredrickjasin.freelancer_app.data.Repository.ReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel(
    private val repository: ReviewsRepository = ReviewsRepository()
) : ViewModel() {

    // -------------------------
    // REVIEWS LIST
    // -------------------------
    private val _reviews = MutableStateFlow<List<Reviews>>(emptyList())
    val reviews: StateFlow<List<Reviews>> = _reviews

    // -------------------------
    // INPUT STATES
    // -------------------------
    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    // -------------------------
    // UI STATES
    // -------------------------
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    // -------------------------
    // UPDATE RATING
    // -------------------------
    fun setRating(value: Int) {

        _rating.value = value.coerceIn(1, 5)

        _error.value = null
    }

    // -------------------------
    // UPDATE COMMENT
    // -------------------------
    fun setComment(value: String) {

        _comment.value = value

        _error.value = null
    }

    // -------------------------
    // LOAD REVIEWS
    // -------------------------
    fun loadReviews(freelancerId: String) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                val result =
                    repository.getReviewsByFreelancer(freelancerId)

                _reviews.value =
                    result.sortedByDescending { it.createdAt }

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Failed to load reviews"

            } finally {

                _isLoading.value = false
            }
        }
    }

    // -------------------------
    // SUBMIT REVIEW
    // -------------------------
    fun submitReview(
        freelancerId: String,
        clientId: String
    ) {

        if (_rating.value == 0) {

            _error.value = "Please select rating"

            return
        }

        if (_comment.value.isBlank()) {

            _error.value = "Please enter review"

            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null
            _success.value = false

            try {

                val review = Reviews(
                    freelancerId = freelancerId,
                    clientId = clientId,
                    rating = _rating.value,
                    comment = _comment.value.trim()
                )

                repository.saveReview(review)

                // RELOAD REVIEWS
                loadReviews(freelancerId)

                // CLEAR INPUTS
                _rating.value = 0
                _comment.value = ""

                _success.value = true

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Failed to submit review"

            } finally {

                _isLoading.value = false
            }
        }
    }

    // -------------------------
    // DELETE REVIEW
    // -------------------------
    fun deleteReview(
        reviewId: String,
        freelancerId: String
    ) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                repository.deleteReview(reviewId)

                // REFRESH REVIEWS
                loadReviews(freelancerId)

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Failed to delete review"

            } finally {

                _isLoading.value = false
            }
        }
    }

    // -------------------------
    // CLEAR ERROR
    // -------------------------
    fun clearError() {

        _error.value = null
    }

    // -------------------------
    // CLEAR SUCCESS STATE
    // -------------------------
    fun clearState() {

        _success.value = false
    }
}