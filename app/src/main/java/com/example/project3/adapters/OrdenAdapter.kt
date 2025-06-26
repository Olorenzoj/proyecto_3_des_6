package com.example.project3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.models.Orden
import java.text.SimpleDateFormat
import java.util.*

class OrdenAdapter(
    private var ordenes: List<Orden>,
    private val onItemClick: (Orden) -> Unit
) : RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {

    inner class OrdenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tvOrdenId)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_orden, parent, false)
        return OrdenViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden = ordenes[position]
        holder.tvId.text = "Orden #${orden.idOrden}"
        holder.tvFecha.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        holder.itemView.setOnClickListener { onItemClick(orden) }
    }

    override fun getItemCount() = ordenes.size

    fun updateList(newList: List<Orden>) {
        ordenes = newList
        notifyDataSetChanged()
    }
}