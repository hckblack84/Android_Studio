package com.actividad_22.data.local
import android.R
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
class Product(
    @PrimaryKey(autoGenerate = true) val id_product:Long = 0,
    val name_product:String,
    val price_product:Double,
    val description_product:String,
    val image_product:String,
    val category_product: Int
) {

}