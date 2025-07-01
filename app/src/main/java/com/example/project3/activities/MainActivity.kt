package com.example.project3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.project3.database.DatabaseHelper
import com.example.project3.fragments.ClientesFragment
import com.example.project3.fragments.OrdenesFragment
import com.example.project3.fragments.ProductosFragment
import com.example.project3.models.Cliente
import com.example.project3.models.Producto
import androidx.core.view.WindowCompat
import com.example.project3.models.DetalleOrden
import com.example.project3.models.Orden

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        insertarDatosDePrueba()

        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)


        windowInsetsController.isAppearanceLightStatusBars = false

        // Referencias a los botones
        val btnClientes: Button = findViewById(R.id.btnClientes)
        val btnProductos: Button = findViewById(R.id.btnProductos)
        val btnOrdenes: Button = findViewById(R.id.btnOrdenes)

        // Cargar fragmento inicial
        cargarFragmento(ClientesFragment())

        // Listeners de los botones
        btnClientes.setOnClickListener {
            cargarFragmento(ClientesFragment())
        }

        btnProductos.setOnClickListener {
            cargarFragmento(ProductosFragment())
        }

        btnOrdenes.setOnClickListener {
            cargarFragmento(OrdenesFragment())
        }
    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun insertarDatosDePrueba() {
        // CLIENTES
        if (dbHelper.getAllClientes().isEmpty()) {
            dbHelper.insertCliente(Cliente(0, "Juan Pérez", "juan@example.com"))
            dbHelper.insertCliente(Cliente(0, "María García", "maria@example.com"))
            dbHelper.insertCliente(Cliente(0, "Oscar Lorenzo", "oscar@example.com"))
            dbHelper.insertCliente(Cliente(0, "Eric Marin", "eric@example.com"))
            dbHelper.insertCliente(Cliente(0, "Raul Asencio", "raul@example.com"))
            dbHelper.insertCliente(Cliente(0, "Lionel Messi", "messi@example.com"))
            dbHelper.insertCliente(Cliente(0, "Cristiano Ronaldo", "cr7@example.com"))
        }

        // PRODUCTOS
        if (dbHelper.getAllProductos().isEmpty()) {
            dbHelper.insertProducto(Producto(0, "Laptop", 1200.0))
            dbHelper.insertProducto(Producto(0, "Teléfono", 800.0))
            dbHelper.insertProducto(Producto(0, "Tablet", 500.0))
            dbHelper.insertProducto(Producto(0, "Monitor", 300.0))
            dbHelper.insertProducto(Producto(0, "Teclado", 50.0))
            dbHelper.insertProducto(Producto(0, "Mouse", 25.0))
        }

        // ORDENES
        if (dbHelper.getAllOrdenes().isEmpty()) {
            dbHelper.insertOrden(Orden(0, 1, "2025-06-25"))
            dbHelper.insertOrden(Orden(0, 2, "2025-06-26"))
            dbHelper.insertOrden(Orden(0, 3, "2025-06-27"))
            dbHelper.insertOrden(Orden(0, 1, "2025-06-28"))
        }

        // DETALLE ORDEN
        // Solo insertar si no hay detalles existentes
        val detallesOrden1 = listOf(
            DetalleOrden(0, 1, 1, 2, 1200.0),  // 2 Laptops
            DetalleOrden(0, 1, 2, 1, 800.0)    // 1 Teléfono
        )

        val detallesOrden2 = listOf(
            DetalleOrden(0, 2, 3, 3, 500.0),   // 3 Tablets
            DetalleOrden(0, 2, 4, 1, 300.0)    // 1 Monitor
        )

        val detallesOrden3 = listOf(
            DetalleOrden(0, 3, 5, 5, 50.0),    // 5 Teclados
            DetalleOrden(0, 3, 6, 2, 25.0)     // 2 Mouse
        )

        val detallesOrden4 = listOf(
            DetalleOrden(0, 4, 1, 1, 1200.0),  // 1 Laptop
            DetalleOrden(0, 4, 3, 1, 500.0)    // 1 Tablet
        )

        val detallesTotales = detallesOrden1 + detallesOrden2 + detallesOrden3 + detallesOrden4
        if (detallesTotales.isNotEmpty()) {
            detallesTotales.forEach { detalle ->
                dbHelper.insertDetalleOrden(detalle)
            }
        }
    }

}