package com.example.project3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.project3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnClientes = findViewById<Button>(R.id.btnClientes)
        val btnProductos = findViewById<Button>(R.id.btnProductos)
        val btnOrdenes = findViewById<Button>(R.id.btnOrdenes)
        // Navegación a las diferentes secciones
        btnClientes.setOnClickListener {
            startActivity(Intent(this, ClientesActivity::class.java))
        }

        btnProductos.setOnClickListener {
            startActivity(Intent(this, ProductosActivity::class.java))
        }

        btnOrdenes.setOnClickListener {
            startActivity(Intent(this, OrdenesActivity::class.java))
        }
    }
}