package com.example.project3.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.project3.models.Cliente
import com.example.project3.models.Producto
import com.example.project3.models.Orden
import com.example.project3.models.DetalleOrden

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Tienda.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tablas.CREATE_TABLE_CLIENTES)
        db.execSQL(Tablas.CREATE_TABLE_PRODUCTOS)
        db.execSQL(Tablas.CREATE_TABLE_ORDENES)
        db.execSQL(Tablas.CREATE_TABLE_DETALLE_ORDEN)
        }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Clientes")
        db.execSQL("DROP TABLE IF EXISTS Productos")
        db.execSQL("DROP TABLE IF EXISTS Ordenes")
        db.execSQL("DROP TABLE IF EXISTS DetalleOrden")
        onCreate(db)
    }

    // ---------------- CLIENTES ----------------
    fun insertCliente(cliente: Cliente): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("correo", cliente.correo)
        }
        return db.insert("Clientes", null, values)
    }

    fun getAllClientes(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Clientes", null)
        with(cursor) {
            while (moveToNext()) {
                val cliente = Cliente(
                    idCliente = getInt(getColumnIndexOrThrow("idCliente")),
                    nombre = getString(getColumnIndexOrThrow("nombre")),
                    correo = getString(getColumnIndexOrThrow("correo"))
                )
                clientes.add(cliente)
            }
            close()
        }
        return clientes
    }

    fun getClienteById(idCliente: Int): Cliente? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Clientes WHERE idCliente = ?", arrayOf(idCliente.toString()))

        return if (cursor.moveToFirst()) {
            Cliente(
                idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("idCliente")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
            )
        } else {
            null
        }.also { cursor.close() }
    }


    fun updateCliente(cliente: Cliente): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("correo", cliente.correo)
        }
        return db.update("Clientes", values, "idCliente = ?", arrayOf(cliente.idCliente.toString()))
    }

    fun deleteCliente(idCliente: Int): Int {
        val db = writableDatabase
        return db.delete("Clientes", "idCliente = ?", arrayOf(idCliente.toString()))
    }

    // ---------------- PRODUCTOS ----------------
    fun insertProducto(producto: Producto): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombreProducto", producto.nombreProducto)
            put("precio", producto.precio)
        }
        return db.insert("Productos", null, values)
    }

    fun getAllProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Productos", null)
        with(cursor) {
            while (moveToNext()) {
                val producto = Producto(
                    idProducto = getInt(getColumnIndexOrThrow("idProducto")),
                    nombreProducto = getString(getColumnIndexOrThrow("nombreProducto")),
                    precio = getDouble(getColumnIndexOrThrow("precio"))
                )
                productos.add(producto)
            }
            close()
        }
        return productos
    }

    fun getProductoById(idProducto: Int): Producto? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Productos WHERE idProducto = ?", arrayOf(idProducto.toString()))

        return if (cursor.moveToFirst()) {
            Producto(
                idProducto = cursor.getInt(cursor.getColumnIndexOrThrow("idProducto")),
                nombreProducto = cursor.getString(cursor.getColumnIndexOrThrow("nombreProducto")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
            )
        } else {
            null
        }.also { cursor.close() }
    }

    fun updateProducto(producto: Producto): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombreProducto", producto.nombreProducto)
            put("precio", producto.precio)
        }
        return db.update(
            "Productos",
            values,
            "idProducto = ?",
            arrayOf(producto.idProducto.toString())
        )
    }

    fun deleteProducto(idProducto: Int): Int {
        val db = writableDatabase
        return db.delete("Productos", "idProducto = ?", arrayOf(idProducto.toString()))
    }

    // ---------------- ORDENES ----------------
    fun insertOrden(orden: Orden): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idCliente", orden.idCliente)
            put("IdFecha", orden.IdFecha)
        }
        return db.insert("Ordenes", null, values)
    }

    fun getOrdenById(idOrden: Int): Orden? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Ordenes WHERE idOrden = ?", arrayOf(idOrden.toString()))

        return if (cursor.moveToFirst()) {
            Orden(
                idOrden = cursor.getInt(cursor.getColumnIndexOrThrow("idOrden")),
                idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("idCliente")),
                IdFecha = cursor.getString(cursor.getColumnIndexOrThrow("IdFecha"))
            )
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    // En DatabaseHelper.kt
    fun getDetallesPorOrdenId(idOrden: Int): List<DetalleOrden> {
        val detalles = mutableListOf<DetalleOrden>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM DetalleOrden WHERE idOrden = ?",
            arrayOf(idOrden.toString())
        )

        with(cursor) {
            while (moveToNext()) {
                val detalle = DetalleOrden(
                    idDetalle = getInt(getColumnIndexOrThrow("idDetalle")),
                    idOrden = getInt(getColumnIndexOrThrow("idOrden")),
                    idProducto = getInt(getColumnIndexOrThrow("idProducto")),
                    cantidad = getInt(getColumnIndexOrThrow("cantidad")),
                    precioUnitario = getDouble(getColumnIndexOrThrow("precioUnitario"))
                )
                detalles.add(detalle)
            }
            close()
        }
        return detalles
    }

    fun getAllOrdenes(): List<Orden> {
        val ordenes = mutableListOf<Orden>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Ordenes", null)
        with(cursor) {
            while (moveToNext()) {
                val orden = Orden(
                    idOrden = getInt(getColumnIndexOrThrow("idOrden")),
                    idCliente = getInt(getColumnIndexOrThrow("idCliente")),
                    IdFecha = getString(getColumnIndexOrThrow("IdFecha"))
                )
                ordenes.add(orden)
            }
            close()
        }
        return ordenes
    }

    fun updateOrden(orden: Orden): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idCliente", orden.idCliente)
            put("IdFecha", orden.IdFecha)
        }
        return db.update("Ordenes", values, "idOrden = ?", arrayOf(orden.idOrden.toString()))
    }

    fun deleteOrden(idOrden: Int): Int {
        val db = writableDatabase
        return db.delete("Ordenes", "idOrden = ?", arrayOf(idOrden.toString()))
    }

    // ---------------- DETALLE ORDEN ----------------
    fun insertDetalleOrden(detalle: DetalleOrden): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idOrden", detalle.idOrden)
            put("idProducto", detalle.idProducto)
            put("cantidad", detalle.cantidad)
            put("precioUnitario", detalle.precioUnitario)
        }
        return db.insert("DetalleOrden", null, values)
    }

    fun updateDetalleOrden(detalle: DetalleOrden): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idOrden", detalle.idOrden)
            put("idProducto", detalle.idProducto)
            put("cantidad", detalle.cantidad)
            put("precioUnitario", detalle.precioUnitario)
        }
        return db.update(
            "DetalleOrden",
            values,
            "idDetalle = ?",
            arrayOf(detalle.idDetalle.toString())
        )
    }
}