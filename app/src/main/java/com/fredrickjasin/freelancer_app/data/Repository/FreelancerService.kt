package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.FreelancerProfile

interface FreelancerService {
    suspend fun fetchProfile(userId: String): FreelancerProfile
    suspend fun saveProfile(profile: FreelancerProfile)
}