package com.example.project3.models

data class Cliente(
    val idCliente: Int,
    val nombre: String,
    val correo: String,
    val telefono: String? = null // Campo adicional
)