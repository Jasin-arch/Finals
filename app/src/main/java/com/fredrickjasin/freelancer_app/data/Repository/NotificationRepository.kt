package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationRepository : NotificationService {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    override suspend fun sendNotification(notification: Notification) {
        val currentList = _notifications.value.toMutableList()
        currentList.add(0, notification)
        _notifications.value = currentList
    }

    override suspend fun getNotifications(): List<Notification> {
        return _notifications.value
    }

    companion object {
        private var instance: NotificationRepository? = null
        fun getInstance(): NotificationRepository {
            if (instance == null) {
                instance = NotificationRepository()
            }
            return instance!!
        }
    }
}
