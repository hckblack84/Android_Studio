package com.actividad_22.apiRest.model

data class Product(
    val idProduct:Int,
    val nameProduct:String,
    val priceProduct:Int,
    val descriptionProduct:String,
    val urlImage:String,
    val stockProduct:Int
    ){

}
