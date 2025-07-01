package com.example.project3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.adapters.ClienteAdapter
import com.example.project3.database.DatabaseHelper

class ClientesFragment : Fragment() {
        private lateinit var dbHelper: DatabaseHelper
        private lateinit var adapter: ClienteAdapter

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_clientes, container, false)

            // Inicializar base de datos
            dbHelper = DatabaseHelper(requireContext())

            // Configurar RecyclerView
            val recyclerView: RecyclerView = view.findViewById(R.id.rvClientes)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Obtener clientes de la base de datos
            val clientes = dbHelper.getAllClientes()

            // Configurar adapter
            adapter = ClienteAdapter(clientes)
            recyclerView.adapter = adapter

            return view
        }

        override fun onResume() {
            super.onResume()
            // Actualizar lista cuando el fragment se vuelve visible
            val clientes = dbHelper.getAllClientes()
            adapter.clientes = clientes
            adapter.notifyDataSetChanged()
        }
}