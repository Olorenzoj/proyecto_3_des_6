package com.example.project3.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.activities.CrearOrdenActivity
import com.example.project3.activities.DetalleOrdenActivity
import com.example.project3.adapters.OrdenAdapter
import com.example.project3.database.DatabaseHelper

class OrdenesFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: OrdenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ordenes, container, false)
        dbHelper = DatabaseHelper(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvOrdenes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val ordenes = dbHelper.getAllOrdenes()

        adapter = OrdenAdapter(
            ordenes = ordenes,
            obtenerNombreCliente = { idCliente ->
                dbHelper.getClienteById(idCliente)?.nombre ?: "Cliente desconocido"
            },
            onItemClick = { orden ->
                val intent = Intent(requireContext(), DetalleOrdenActivity::class.java)
                intent.putExtra("orden_id", orden.idOrden)
                startActivity(intent)
            }
        )
        // Click en el FAB para crear nueva orden
        val fab: View = view.findViewById(R.id.fabNuevaOrden)
        fab.setOnClickListener {
            val intent = Intent(requireContext(), CrearOrdenActivity::class.java)
            startActivity(intent)
        }


        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val ordenes = dbHelper.getAllOrdenes()
        adapter.ordenes = ordenes
        adapter.notifyDataSetChanged()
    }
}
