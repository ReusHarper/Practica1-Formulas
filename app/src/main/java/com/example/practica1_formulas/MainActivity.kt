package com.example.practica1_formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.practica1_formulas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var operadores = arrayListOf<Double>()

    private val tp_form = arrayListOf<String>("voltaje", "corriente", "resistencia")

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_Practica1Formulas)

        // Creacion de binding
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    // Se transforma los datos ingresados por el usuario a formato decimal
    fun getDatos(): ArrayList<Double> {

        var operadoresTmp = arrayListOf<Double>(0.0, 0.0)

        try {
            operadoresTmp[0] = binding.etnd1.toString().toDouble()
            operadoresTmp[1] = binding.etnd1.toString().toDouble()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: Por favor ingrese solo datos numéricos",
                Toast.LENGTH_SHORT
            ).show()
        }

        return operadoresTmp
    }

    // Se define la operacion seleccionada por el usuario brindada por el menu en forma de spinner
    fun setOperacion(tipo: String, operadores: ArrayList<Double>): Double {

        var resultado = 0.0

        try {
            resultado =
                when {
                    tipo.equals("voltaje") -> voltaje(operadores)
                    tipo.equals("corriente") -> corriente(operadores)
                    tipo.equals("resistencia") -> resistencia(operadores)
                else -> { 0.0 }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: La operación no se logró realizar",
                Toast.LENGTH_SHORT
            ).show()
        }

        return resultado
    }

    // Colocacion de resultado

    // Para este ejercicio se realizara cada una de las operaciones por separado, teniendo en cuenta
    // sus metodos de forma independiente
    fun voltaje(operadores: ArrayList<Double>): Double {

        var resultado = 0.0

        try {
            resultado = operadores[0] * operadores[1]
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: La operación de la diferencia de potencial no se logró concretar.",
                Toast.LENGTH_SHORT
            ).show()
        }

        return resultado
    }

    fun corriente(operadores: ArrayList<Double>): Double {

        var resultado = 0.0

        try {
            if (operadores[1] != 0.0) resultado = operadores[0] / operadores[1]
            else {
                Toast.makeText(
                    this,
                    "Error: El valor de la resistencia no puede ser cero. Por favor vuelva a ingresar el dato de forma correcta.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: La operación de la intensidad de corriente no se logró concretar.",
                Toast.LENGTH_SHORT
            ).show()
        }

        return resultado
    }

    fun resistencia(operadores: ArrayList<Double>): Double {

        var resultado = 0.0

        try {
            if (operadores[1] != 0.0) resultado = operadores[0] / operadores[1]
            else {
                Toast.makeText(
                    this,
                    "Error: El valor de la intensidad de corriente no puede ser cero. Por favor vuelva a ingresar el dato de forma correcta.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: La operación de la intensidad de corriente no se logró concretar.",
                Toast.LENGTH_SHORT
            ).show()
        }

        return resultado
    }

    // Se recaban y envian los datos ingresados de los EditText a la funcion encargada de efectuar
    // los calculos correspondiente
    fun calcular(view: View) {

        // Se verifica que no halla datos sin ingresar por parte del usuario
        with(binding) {
            when {
                etnd1.text.toString().isEmpty() -> {
                    etnd1.error = "Dato 1 no ingresado."
                    Toast.makeText(
                        this@MainActivity,
                        "Por favor ingrese un número.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                etnd2.text.toString().isEmpty() -> {
                    etnd1.error = "Dato 2 no ingresado."
                    Toast.makeText(
                        this@MainActivity,
                        "Por favor ingrese un número.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    operadores = getDatos()
                    setOperacion("voltaje", operadores)
                }
            }
        }

    }

}
/*

 */