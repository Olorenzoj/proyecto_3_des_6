package com.example.project3.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.adapters.ClienteAdapter
import com.example.project3.database.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClientesActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.rvClientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ClienteAdapter(dbHelper.getAllClientes()) { cliente ->
            // Editar cliente (usar Intent para edición)
        }
        recyclerView.adapter = adapter

        // Botón para agregar nuevo cliente
        findViewById<FloatingActionButton>(R.id.fabAgregarCliente).setOnClickListener {
            val intent = Intent(this, EditarClienteActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_CLIENTE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_CLIENTE && resultCode == RESULT_OK) {
            adapter.updateList(dbHelper.getAllClientes()) // Actualizar lista
        }
    }
}