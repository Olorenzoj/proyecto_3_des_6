package com.example.project3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.adapters.ProductoAdapter
import com.example.project3.database.DatabaseHelper
import com.example.project3.models.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductosFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        // Inicializar base de datos
        dbHelper = DatabaseHelper(requireContext())

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.rvProductos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Obtener productos de la base de datos
        val productos = dbHelper.getAllProductos()

        // Configurar adapter
        adapter = ProductoAdapter(productos) { producto ->
            // Manejar clic en producto
            mostrarDetalleProducto(producto)
        }
        recyclerView.adapter = adapter

        // Configurar FAB
        val fabAgregar: FloatingActionButton = view.findViewById(R.id.fabAgregarProducto)
        fabAgregar.setOnClickListener {
            // Lógica para agregar nuevo producto
            agregarNuevoProducto()
        }

        return view
    }

    private fun mostrarDetalleProducto(producto: Producto) {
        // Implementar navegación a detalle de producto
    }

    private fun agregarNuevoProducto() {
        // Implementar lógica para agregar nuevo producto
    }

    override fun onResume() {
        super.onResume()
        // Actualizar lista cuando el fragment se vuelve visible
        val productos = dbHelper.getAllProductos()
        adapter.productos = productos
        adapter.notifyDataSetChanged()
    }
}
