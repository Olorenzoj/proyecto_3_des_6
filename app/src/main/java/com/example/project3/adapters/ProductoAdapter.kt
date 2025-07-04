package com.example.project3.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.models.Producto

class ProductoAdapter(
    var productos: List<Producto>,
    private val onItemClick: (Producto) -> Unit // Callback obligatorio para el click
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFotoProducto: ImageView = itemView.findViewById(R.id.ivFotoProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)

        init {
            itemView.setOnClickListener {
                val producto = productos[adapterPosition]
                onItemClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.tvNombreProducto.text = producto.nombreProducto
        holder.tvPrecio.text = "Precio: $${producto.precio}"

        // Como este es el listado general, ocultamos la cantidad
        holder.tvCantidad.visibility = View.GONE

        // Cargar foto del producto
        if (!producto.fotoUri.isNullOrEmpty()) {
            try {
                val uri = Uri.parse(producto.fotoUri)
                holder.ivFotoProducto.setImageURI(uri)
                holder.ivFotoProducto.scaleType = ImageView.ScaleType.CENTER_CROP
            } catch (e: Exception) {
                // Si hay error al cargar la imagen, usar imagen por defecto
                holder.ivFotoProducto.setImageResource(R.drawable.ic_producto_default)
                holder.ivFotoProducto.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        } else {
            // Si no hay foto, usar imagen por defecto
            holder.ivFotoProducto.setImageResource(R.drawable.ic_producto_default)
            holder.ivFotoProducto.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }

    override fun getItemCount() = productos.size
}