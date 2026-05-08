package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Notification

interface NotificationService {
    suspend fun sendNotification(notification: Notification)
    suspend fun getNotifications(): List<Notification>
}
