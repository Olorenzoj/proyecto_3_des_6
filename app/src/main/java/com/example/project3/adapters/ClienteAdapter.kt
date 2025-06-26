package com.example.project3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.models.Cliente

class ClienteAdapter(
    private var clientes: List<Cliente>,
    private val onItemClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.tvNombre.text = cliente.nombre
        holder.tvCorreo.text = cliente.correo
        holder.itemView.setOnClickListener { onItemClick(cliente) }
    }

    override fun getItemCount() = clientes.size

    fun updateList(newList: Li