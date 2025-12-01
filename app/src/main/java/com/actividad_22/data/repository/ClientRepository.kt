package com.actividad_22.data.repository

import com.actividad_22.data.local.Client
import com.actividad_22.data.local.ClientDAO
import kotlinx.coroutines.flow.Flow

open class ClientRepository(private val clientDAO: ClientDAO) {

    val allClients: Flow<List<Client>> = clientDAO.findAll()

    suspend fun insertClient(client: Client) {
        clientDAO.insertClient(client)
    }

    suspend fun updateClient(client: Client) {
        clientDAO.updateClient(client)
    }

    suspend fun deleteClient(client: Client) {
        clientDAO.deleteClient(client)
    }

    suspend fun clearClientTable() {
        clientDAO.clearClientsTable()
    }


    suspend fun login(email: String, password: String): Client? {
        return clientDAO.login(email, password)
    }

    suspend fun getClientById(id: Long): Client? {
        return clientDAO.getClientById(id)
    }

    suspend fun getClientByEmail(email: String): Client? {
        return clientDAO.getClientByEmail(email)
    }
}
