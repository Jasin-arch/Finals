package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Profile

interface ProfileService {
    suspend fun saveProfile(profile: Profile)
    suspend fun getProfile(): Profile?
}