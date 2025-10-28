package com.actividad_22.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
class Client(
    @PrimaryKey(autoGenerate = true) val id_client:Long = 0,
    val name_client:String,
    val email_client:String,
    val password_client:String,
    val direction_client:String
) {

}