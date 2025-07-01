package com.example.project3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.models.Orden

class OrdenAdapter(
    var ordenes: List<Orden>,
    private val obtenerNombreCliente: (Int) -> String,
    private val onItemClick: (Orden) -> Unit
) : RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {

    inner class OrdenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrden: TextView = itemView.findViewById(R.id.tvOrden)
        val tvNombreCliente: TextView = itemView.findViewById(R.id.tvNombreCliente)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(ordenes[position])
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orden, parent, false)
        return OrdenViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden = ordenes[position]
        holder.tvOrden.text = "Orden #${orden.idOrden}"
        holder.tvNombreCliente.text = "Cliente: ${obtenerNombreCliente(orden.idCliente)}"
        holder.tvFecha.text = "Fecha: ${orden.IdFecha}"
    }


    override fun getItemCount() = ordenes.size
}
