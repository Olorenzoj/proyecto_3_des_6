package com.example.project3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3.R
import com.example.project3.adapters.ProductoAdapter
import com.example.project3.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_productos.*

class ProductosActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        rvProductos.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(dbHelper.getAllProductos()) { producto ->
            val intent = Intent(this, EditarProductoActivity::class.java).apply {
                putExtra("idProducto", producto.idProducto)
            }
            startActivityForResult(intent, REQUEST_EDIT_PRODUCTO)
        }
        rvProductos.adapter = adapter

        // Botón para agregar producto
        fabAgregarProducto.setOnClickListener {
            startActivityForResult(Intent(this, EditarProductoActivity::class.java), REQUEST_ADD_PRODUCTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_ADD_PRODUCTO || requestCode == REQUEST_EDIT_PRODUCTO) && resultCode == RESULT_OK) {
            adapter.updateList(dbHelper.getAllProductos()) // Actualizar lista
        }
    }

    companion object {
        const val REQUEST_ADD_PRODUCTO = 1
        const val REQUEST_EDIT_PRODUCTO = 2
    }
}