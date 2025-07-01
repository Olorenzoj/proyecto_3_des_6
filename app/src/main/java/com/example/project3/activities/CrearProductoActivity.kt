package com.example.project3.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project3.R
import com.example.project3.database.DatabaseHelper
import com.example.project3.models.Producto

class CrearProductoActivity : AppCompatActivity() {

    private lateinit var etNombreProducto: EditText
    private lateinit var etPrecio: EditText
    private lateinit var ivFotoProducto: ImageView
    private lateinit var btnSeleccionarFoto: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private lateinit var dbHelper: DatabaseHelper
    private var fotoUri: Uri? = null

    private val PERMISSION_REQUEST_CODE = 100

    // Launcher para seleccionar imagen de galería
    private val galeriaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                fotoUri = uri
                ivFotoProducto.setImageURI(uri)
                // Hacer la imagen más visible al usuario
                ivFotoProducto.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    // Launcher para tomar foto con cámara
    private val camaraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            fotoUri?.let { uri ->
                ivFotoProducto.setImageURI(uri)
                ivFotoProducto.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        initViews()
        initDatabase()
        setupClickListeners()
        setupToolbar()
    }

    private fun initViews() {
        etNombreProducto = findViewById(R.id.etNombreProducto)
        etPrecio = findViewById(R.id.etPrecio)
        ivFotoProducto = findViewById(R.id.ivFotoProducto)
        btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
    }

    private fun initDatabase() {
        dbHelper = DatabaseHelper(this)
    }

    private fun setupToolbar() {
        supportActionBar?.title = "Crear Producto"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupClickListeners() {
        btnSeleccionarFoto.setOnClickListener {
            mostrarOpcionesFoto()
        }

        btnGuardar.setOnClickListener {
            guardarProducto()
        }

        btnCancelar.setOnClickListener {
            finish()
        }

        ivFotoProducto.setOnClickListener {
            mostrarOpcionesFoto()
        }
    }

    private fun mostrarOpcionesFoto() {
        val opciones = arrayOf("Galería", "Cámara")

        AlertDialog.Builder(this)
            .setTitle("Seleccionar foto")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> abrirGaleria()
                    1 -> abrirCamara()
                }
            }
            .show()
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galeriaLauncher.launch(intent)
    }

    private fun abrirCamara() {
        if (verificarPermisosCamara()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Crear URI temporal para la foto
            fotoUri = crearUriTemporal()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)
            camaraLauncher.launch(intent)
        } else {
            solicitarPermisosCamara()
        }
    }

    private fun verificarPermisosCamara(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun solicitarPermisosCamara() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun crearUriTemporal(): Uri {
        val fileName = "producto_${System.currentTimeMillis()}.jpg"
        val contentValues = android.content.ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MiTienda")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    private fun guardarProducto() {
        val nombre = etNombreProducto.text.toString().trim()
        val precioTexto = etPrecio.text.toString().trim()

        // Validaciones
        if (nombre.isEmpty()) {
            etNombreProducto.error = "El nombre del producto es obligatorio"
            etNombreProducto.requestFocus()
            return
        }

        if (precioTexto.isEmpty()) {
            etPrecio.error = "El precio es obligatorio"
            etPrecio.requestFocus()
            return
        }

        val precio = try {
            precioTexto.toDouble()
        } catch (e: NumberFormatException) {
            etPrecio.error = "Ingrese un precio válido"
            etPrecio.requestFocus()
            return
        }

        if (precio <= 0) {
            etPrecio.error = "El precio debe ser mayor a 0"
            etPrecio.requestFocus()
            return
        }

        // Crear producto
        val producto = Producto(
            nombreProducto = nombre,
            precio = precio,
            fotoUri = fotoUri?.toString()
        )

        // Guardar en base de datos
        val resultado = dbHelper.insertProducto(producto)

        if (resultado != -1L) {
            Toast.makeText(this, "Producto creado exitosamente", Toast.LENGTH_SHORT).show()

            // Establecer resultado para que el fragment se actualice
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Error al crear el producto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}