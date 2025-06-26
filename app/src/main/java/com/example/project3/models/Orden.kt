package com.example.project3.models

data class Orden(
    val idOrden: Int,
    val idCliente: Int,
    val fecha: String,
    val estado: String = "Pendiente" // Campo adicional
)