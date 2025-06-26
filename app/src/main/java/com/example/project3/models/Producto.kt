package com.example.project3.models

data class Producto(
    val idProducto: Int,
    val nombreProducto: String,
    val precio: Double,
    val stock: Int = 0 // Campo adicional
)