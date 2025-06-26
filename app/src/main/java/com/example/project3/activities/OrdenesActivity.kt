package com.example.project3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3.R
import com.example.project3.adapters.OrdenAdapter
import com.example.project3.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_ordenes.*

class OrdenesActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: OrdenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes)
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        rvOrdenes.layoutManager = LinearLayoutManager(this)
        adapter = OrdenAdapter(dbHelper.getAllOrdenes()) { orden ->
            val intent = Intent(this, DetalleOrdenActivity::class.java).apply {
                putExtra("idOrden", orden.idOrden)
            }
            startActivity(intent)
        }
        rvOrdenes.adapter = adapter

        // Botón para nueva orden
        fabNuevaOrden.setOnClickListener {
            startActivity(Intent(this, CrearOrdenActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateList(dbHelper.getAllOrdenes()) // Actualizar al volver
    }
}