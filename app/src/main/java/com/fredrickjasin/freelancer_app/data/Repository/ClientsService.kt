package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Clients

interface ClientsService {
    suspend fun getClient(userId: String): Clients?
    suspend fun saveClient(client: Clients)
    suspend fun deleteClient(userId: String)
}