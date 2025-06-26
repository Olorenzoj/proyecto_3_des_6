package com.example.project3.activities

import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.project3.R
import com.example.project3.database.DatabaseHelper
import com.example.project3.models.DetalleOrden

class CrearOrdenActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var spinnerClientes: Spinner
    private lateinit var spinnerProductos: Spinner
    private lateinit var listaProductos: MutableList<DetalleOrden>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_orden)
        dbHelper = DatabaseHelper(this)
        listaProductos = mutableListOf()

        // Configurar Spinner de Clientes
        spinnerClientes = findViewById(R.id.spinnerClientes)
        val clientes = dbHelper.getAllClientes()
        val clienteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, clientes.map { it.nombre })
        spinnerClientes.adapter = clienteAdapter

        // Configurar Spinner de Productos
        spinnerProductos = findViewById(R.id.spinnerProductos)
        val productos = dbHelper.getAllProductos()
        val productoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productos.map { it.nombreProducto })
        spinnerProductos.adapter = productoAdapter

        // Agregar producto a la orden
        findViewById<Button>(R.id.btnAgregarProducto).setOnClickListener {
            val productoSeleccionado = productos[spinnerProductos.selectedItemPosition]
            val cantidad = findViewById<EditText>(R.id.etCantidad).text.toString().toIntOrNull() ?: 1
            listaProductos.add(DetalleOrden(0, 0, productoSeleccionado.idProducto, cantidad))
            actualizarListaProductos()
        }

        // Guardar orden
        findViewById<Button>(R.id.btnGuardarOrden).setOnClickListener {
            guardarOrden()
        }
    }

    private fun guardarOrden() {
        val cliente = dbHelper.getAllClientes()[spinnerClientes.selectedItemPosition]
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val idOrden = dbHelper.insertOrden(Orden(0, cliente.idCliente, fecha))

        listaProductos.forEach { detalle ->
            detalle.idOrden = idOrden.toInt()
            dbHelper.insertDetalleOrden(detalle)
        }
        finish()
    }
}