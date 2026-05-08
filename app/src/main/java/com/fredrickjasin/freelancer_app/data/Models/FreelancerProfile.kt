package com.fredrickjasin.freelancer_app.data.Models
data class FreelancerProfile(
    val id: String = "",
    val username: String = "",
    val profession: String = "",
    val bio: String = "",
    val dateOfBirth: String = "",
    val location: String = "",
    val profileImage: String = "",
    val skills: List<String> = emptyList(),
    val rating: Float = 0f,
    val completedJobs: Int = 0
)