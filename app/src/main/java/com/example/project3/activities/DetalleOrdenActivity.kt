package com.example.project3.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3.adapters.DetalleOrdenAdapter
import com.example.project3.database.DatabaseHelper
import com.example.project3.databinding.ActivityDetalleOrdenBinding
import com.example.project3.models.DetalleOrden

class DetalleOrdenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleOrdenBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var detalleOrdenAdapter: DetalleOrdenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleOrdenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val ordenId = intent.getIntExtra("orden_id", -1)
        if (ordenId != -1) {
            cargarDetallesOrden(ordenId)
        } else {
            finish()  // Cierra la activity si no viene un ID válido
        }

        binding.btnVolver.setOnClickListener {
            finish()  // Simplemente cerrar la actividad para volver
        }

        binding.btnEditarOrden.setOnClickListener {
            // Aquí puedes abrir otra actividad de edición o mostrar un Toast por ahora
            Toast.makeText(this, "Función de editar no implementada todavía", Toast.LENGTH_SHORT).show()
        }

    }

    private fun cargarDetallesOrden(ordenId: Int) {
        val orden = dbHelper.getOrdenById(ordenId)

        orden?.let {
            // Datos generales de la orden
            binding.tvOrdenId.text = "Orden #${it.idOrden}"
            binding.tvFecha.text = "Fecha: ${it.IdFecha}"

            val cliente = dbHelper.getClienteById(it.idCliente)
            binding.tvCliente.text = "Cliente: ${cliente?.nombre ?: "Desconocido"}"
            val detalles = dbHelper.getDetallesPorOrdenId(it.idOrden)
            setupRecyclerView(detalles)

            val total = detalles.sumOf { detalle -> detalle.cantidad * detalle.precioUnitario }
            binding.tvTotal.text = "Total: $${"%.2f".format(total)}"
        } ?: run {
            // Si no encontró la orden
            finish()
        }
    }

    private fun setupRecyclerView(detalles: List<DetalleOrden>) {
        detalleOrdenAdapter = DetalleOrdenAdapter(detalles) { idProducto ->
            dbHelper.getProductoById(idProducto)
        }

        binding.rvDetalles.apply {
            layoutManager = LinearLayoutManager(this@DetalleOrdenActivity)
            adapter = detalleOrdenAdapter
        }
    }

}
