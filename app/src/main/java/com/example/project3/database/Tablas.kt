package com.example.project3.database

object Tablas {
    const val CREATE_TABLE_CLIENTES = """
        CREATE TABLE Clientes (
            idCliente INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            correo TEXT,
            telefono TEXT
        )"""

    const val CREATE_TABLE_DETALLE_ORDEN = """
        CREATE TABLE DetalleOrden (
            idDetalle INTEGER PRIMARY KEY AUTOINCREMENT,
            idOrden INTEGER,
            idProducto INTEGER,
            cantidad INTEGER,
            FOREIGN KEY (idOrden) REFERENCES Ordenes(idOrden),
            FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
        )"""
}