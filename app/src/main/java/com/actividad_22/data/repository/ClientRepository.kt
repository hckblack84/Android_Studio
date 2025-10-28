package com.actividad_22.data.repository

import com.actividad_22.data.local.Client
import com.actividad_22.data.local.ClientDAO
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDAO: ClientDAO) {
    val clients: Flow<List<Client>> = clientDAO.findAll()

    suspend fun selectClient(email: String, password: String):Flow<List<Client>> = clientDAO.findClient(email, password)
    suspend fun insert(client: Client) = clientDAO.insertClient(client)
    suspend fun update(client: Client) = clientDAO.updateClient(client)
    suspend fun delete(client: Client) = clientDAO.deleteClient(client)

}