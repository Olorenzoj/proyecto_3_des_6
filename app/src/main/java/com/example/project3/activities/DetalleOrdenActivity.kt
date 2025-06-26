package com.example.project3.activities

class DetalleOrdenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_orden)

        val idOrden = intent.getIntExtra("idOrden", 0)
        val dbHelper = DatabaseHelper(this)
        val orden = dbHelper.getOrdenById(idOrden)
        val detalles = dbHelper.getDetallesPorOrden(idOrden)

        // Mostrar datos
        findViewById<TextView>(R.id.tvOrdenId).text = "Orden #${orden.idOrden}"
        findViewById<TextView>(R.id.tvFecha).text = "Fecha: ${orden.fecha}"

        // Configurar RecyclerView para detalles
        val recyclerView = findViewById<RecyclerView>(R.id.rvDetalles)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DetalleAdapter(detalles)
    }
}