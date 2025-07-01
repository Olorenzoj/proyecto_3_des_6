package com.example.project3.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3.adapters.DetalleOrdenAdapter
import com.example.project3.database.DatabaseHelper
import com.example.project3.databinding.ActivityCrearOrdenBinding
import com.example.project3.models.Cliente
import com.example.project3.models.DetalleOrden
import com.example.project3.models.Orden
import com.example.project3.models.Producto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrearOrdenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearOrdenBinding
    private lateinit var dbHelper: DatabaseHelper

    private lateinit var clientes: List<Cliente>
    private lateinit var productos: List<Producto>

    // Adapter declarado como variable de clase para actualizar la lista dinámicamente
    private lateinit var adapter: DetalleOrdenAdapter

    // Lista mutable que se usa para mostrar los detalles en el RecyclerView
    private val listaDetalles = mutableListOf<DetalleOrden>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearOrdenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        cargarClientes()
        cargarProductos()
        setupRecyclerView()
        setupEventos()
        mostrarFechaActual()
    }

    private fun cargarClientes() {
        clientes = dbHelper.getAllClientes()
        val nombresClientes = clientes.map { it.nombre }
        binding.spinnerClientes.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresClientes)
    }

    private fun cargarProductos() {
        productos = dbHelper.getAllProductos()
        val nombresProductos = productos.map { it.nombreProducto }
        binding.spinnerProductos.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresProductos)
    }

    private fun setupRecyclerView() {
        // Inicializas el adapter con la lista mutable
        adapter = DetalleOrdenAdapter(listaDetalles) { idProducto ->
            dbHelper.getProductoById(idProducto)
        }
        binding.rvProductosOrden.layoutManager = LinearLayoutManager(this)
        binding.rvProductosOrden.adapter = adapter
    }

    private fun setupEventos() {
        binding.btnAgregarProducto.setOnClickListener {
            val productoSeleccionado = productos.getOrNull(binding.spinnerProductos.selectedItemPosition)
            val cantidadStr = binding.etCantidad.text.toString()

            if (productoSeleccionado != null && cantidadStr.isNotEmpty()) {
                val cantidad = cantidadStr.toIntOrNull() ?: 0
                if (cantidad > 0) {
                    val detalle = DetalleOrden(
                        idDetalle = 0,
                        idOrden = 0, // Temporal, luego se actualizará al guardar la orden
                        idProducto = productoSeleccionado.idProducto,
                        cantidad = cantidad,
                        precioUnitario = productoSeleccionado.precio
                    )
                    listaDetalles.add(detalle)

                    // Notificamos solo la inserción del nuevo elemento para refrescar RecyclerView
                    adapter.notifyItemInserted(listaDetalles.size - 1)

                    calcularTotal()
                    binding.etCantidad.text.clear()
                } else {
                    Toast.makeText(this, "Cantidad inválida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Selecciona un producto y escribe la cantidad", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGuardarOrden.setOnClickListener {
            guardarOrden()
        }
    }

    private fun calcularTotal() {
        val total = listaDetalles.sumOf { it.cantidad * it.precioUnitario }
        binding.tvTotal.text = "Total: $${String.format("%.2f", total)}"
    }

    private fun mostrarFechaActual() {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.tvFecha.text = fechaActual
    }

    private fun guardarOrden() {
        if (listaDetalles.isEmpty()) {
            Toast.makeText(this, "Agrega al menos un producto", Toast.LENGTH_SHORT).show()
            return
        }

        val clienteSeleccionado = clientes.getOrNull(binding.spinnerClientes.selectedItemPosition)
        val fecha = binding.tvFecha.text.toString()

        if (clienteSeleccionado == null) {
            Toast.makeText(this, "Selecciona un cliente", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaOrden = Orden(
            idOrden = 0,
            idCliente = clienteSeleccionado.idCliente,
            IdFecha = fecha
        )

        val idOrdenInsertada = dbHelper.insertOrden(nuevaOrden).toInt()

        if (idOrdenInsertada != -1) {
            for (detalle in listaDetalles) {
                val detalleConOrden = detalle.copy(idOrden = idOrdenInsertada)
                dbHelper.insertDetalleOrden(detalleConOrden)
            }

            Toast.makeText(this, "Orden creada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al guardar la orden", Toast.LENGTH_SHORT).show()
        }
    }
}
