package com.actividad_22.data.repository

import com.actividad_22.data.local.Client
import com.actividad_22.data.local.ClientDAO
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDAO: ClientDAO){

    val allClients: Flow<List<Client>> = clientDAO.findAll()

    suspend fun insertClient(client: Client){
        clientDAO.insertClient(client)
    }

    suspend fun deleteClient(client: Client){
        clientDAO.deleteClient(client)
    }

}