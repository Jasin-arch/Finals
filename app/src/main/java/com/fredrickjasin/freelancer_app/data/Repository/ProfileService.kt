package com.fredrickjasin.freelancer_app.data.remote

import com.fredrickjasin.freelancer_app.data.Models.FreelancerProfile

interface FreelancerApiService {

    suspend fun getProfile(userId: String): FreelancerProfile

    suspend fun updateProfile(profile: FreelancerProfile)

}