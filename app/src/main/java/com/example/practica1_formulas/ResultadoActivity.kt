package com.example.practica1_formulas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.practica1_formulas.databinding.ActivityMainBinding
import com.example.practica1_formulas.databinding.ActivityResultadoBinding

class ResultadoActivity : AppCompatActivity() {

    // Se establece un objeto de tipo binding para acceder a todos los elementos
    // de la vista
    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Creacion de binding
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se recoje la informacion pasada desde la vista MainActivity
        val bundle = intent.extras

        if (bundle != null) {
            val resultado = bundle!!.getString("resultado")
            val unidades = bundle!!.getString("unidades")

            // Insercion del resultado dentro de un textview
            binding.tvResultado.text = "$resultado [$unidades]"
        }
        else {
            Toast.makeText(
                this@ResultadoActivity,
                "Error: No se logro obtener el resultado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun regresar(view: View) {
        // Se genera un intent para poder regresar a la vista principal con el fin de reiniciar
        // la app
        val intent = Intent(this, MainActivity::class.java)

        // Se borra el stack de intents y se reinicia la ap
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}