package com.example.project3.database

object Tablas {
    const val CREATE_TABLE_CLIENTES = """
        CREATE TABLE Clientes (
            idCliente INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            correo TEXT
        )"""

    const val CREATE_TABLE_PRODUCTOS = """
    CREATE TABLE Productos (
        idProducto INTEGER PRIMARY KEY AUTOINCREMENT,
        nombreProducto TEXT NOT NULL,
        precio REAL,
        fotoUri TEXT
    )
"""


    const val CREATE_TABLE_ORDENES ="""
    CREATE TABLE Ordenes (
        idOrden INTEGER PRIMARY KEY AUTOINCREMENT,
        idCliente INTEGER NOT NULL,
        IdFecha TEXT,
        FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
    )"""
    const val CREATE_TABLE_DETALLE_ORDEN = """
    CREATE TABLE DetalleOrden (
        idDetalle INTEGER PRIMARY KEY AUTOINCREMENT,
        idOrden INTEGER,
        idProducto INTEGER,
        cantidad INTEGER,
        precioUnitario REAL,
        FOREIGN KEY (idOrden) REFERENCES Ordenes(idOrden),
        FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
    )"""

}