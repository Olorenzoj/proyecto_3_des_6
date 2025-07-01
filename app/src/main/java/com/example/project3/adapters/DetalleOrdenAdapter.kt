package com.example.project3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.models.DetalleOrden
import com.example.project3.models.Producto

class DetalleOrdenAdapter(
    private val detalles: List<DetalleOrden>,
    private val obtenerProducto: (Int) -> Producto?

) : RecyclerView.Adapter<DetalleOrdenAdapter.DetalleViewHolder>() {

    inner class DetalleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFotoProducto: ImageView = itemView.findViewById(R.id.ivFotoProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return DetalleViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetalleViewHolder, position: Int) {
        val detalle = detalles[position]
        val producto = obtenerProducto(detalle.idProducto)

        holder.tvNombreProducto.text = producto?.nombreProducto ?: "Producto desconocido"
        holder.tvPrecio.text = "Precio Unitario: $${detalle.precioUnitario}"
        holder.tvCantidad.visibility = View.VISIBLE
        holder.tvCantidad.text = "Cantidad: ${detalle.cantidad}"
        holder.ivFotoProducto.setImageResource(R.drawable.ic_producto_default)
    }

    override fun getItemCount() = detalles.size
}
