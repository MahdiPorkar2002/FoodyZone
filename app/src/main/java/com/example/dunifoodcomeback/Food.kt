package com.example.dunifoodcomeback

data class Food(
    var txtSubject: String,
    var txtPrice: String,
    var txtDistance: String,
    var txtCity: String,
    val urlImage: String,
    val ratersCount: Int,
    val rating: Float
)
