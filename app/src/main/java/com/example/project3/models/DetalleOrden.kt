package com.example.project3.models

data class DetalleOrden(
    val idDetalle: Int,
    var idOrden: Int, // Se actualiza después de crear la orden
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: Double // Campo adicional
)