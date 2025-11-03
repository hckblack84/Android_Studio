package com.actividad_22.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
class ProductData(
    @PrimaryKey(autoGenerate = true) val id_product:Long = 0,
    val name_product:String,
    val price_product:Double,
    val description_product:String,
    val image_product:Int,
    val category_product: Int
) {

}