package com.example.project3.database

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

    // Operaciones CRUD para cada tabla...
    fun insertCliente(cliente: Cliente): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("correo", cliente.correo)
            put("telefono", cliente.telefono) // Columna adicional
        }
        return db.insert("Clientes", null, values)
    }

    fun getAllClientes(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Clientes", null)
        // Mapear cursor a lista de clientes
        cursor.close()
        return clientes
    }
    // ... (métodos para Productos, Órdenes, DetalleOrden)
}