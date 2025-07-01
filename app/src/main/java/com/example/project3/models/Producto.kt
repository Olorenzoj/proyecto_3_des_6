package com.example.project3.models

data class Producto(
    val idProducto: Int = 0,
    val nombreProducto: String,
    val precio: Double,
    val fotoUri: String? = null
)