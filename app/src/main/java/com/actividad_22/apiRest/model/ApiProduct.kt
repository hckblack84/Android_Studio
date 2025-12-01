package com.actividad_22.apiRest.model

data class ApiProduct(
    val idProduct:Int,
    val nameProduct:String,
    val categoryProduct:String,
    val distributorProduct:String,
    val linkDistributor:String,
    val priceProduct:Int,
    val descriptionProduct:String,
    val urlImage:String,
    val stockProduct:Int
    ){

}
