package com.example.project3.models

data class DetalleOrden(
    val idDetalle: Int,
    val idOrden: Int,
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: Double
)