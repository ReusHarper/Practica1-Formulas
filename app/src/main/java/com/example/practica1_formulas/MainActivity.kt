package com.example.practica1_formulas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.practica1_formulas.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    // Se establece un objeto de tipo binding para acceder a todos los elementos
    // de la vista
    private lateinit var binding: ActivityMainBinding

    // Se establecen los elementos encargadados de almancenar los valores que \
    // ingresara el usuario junto con las formulas disponibles
    private lateinit var operadores: ArrayList<Double>
    private lateinit var spinner: ArrayAdapter<String>
    private lateinit var formulaSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Practica1Formulas)

        // Creacion de binding
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Conexion de spinner con su clase
        spinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)

        // Asignacion de lista de formulas a spinner
        spinner.addAll(Arrays.asList("voltaje", "corriente", "resistencia"))

        // Agreacion de valores con spinner
        binding.spTipo.onItemSelectedListener = this
        binding.spTipo.adapter = spinner
    }

    // Se transforma los datos ingresados por el usuario a formato decimal
    fun getDatos(): ArrayList<Double> {
        var operadoresTmp = arrayListOf(0.0, 0.0)

        try {
            operadoresTmp[0] = binding.etnd1.text.toString().toDouble()
            operadoresTmp[1] = binding.etnd2.text.toString().toDouble()
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

    // Colocacion de resultado en nueva vista
    fun envioResultado(resultado: Int) {
        //bind
    }

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
    // los calculos correspondientes una vez se presione el boton de calcular
    fun calcular(view: View) {
        // Se genera un intent para poder enviar la informacion calculada a otra vista mediante
        // una activity
        val intent = Intent(this, ResultadoActivity::class.java)

        // Se verifica que no halla datos sin ingresar por parte del usuario
        with(binding) {
            when {
                etnd1.text.toString().isEmpty() || etnd2.text.toString().isEmpty() -> {
                    if (etnd1.text.toString().isEmpty()) {
                        etnd1.error = "Dato 1 no ingresado."
                    }
                    if (etnd2.text.toString().isEmpty()) {
                        etnd2.error = "Dato 2 no ingresado."
                    }
                    Toast.makeText(
                        this@MainActivity,
                        "Por favor ingrese un número.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Si los datos ingresados son correctos se reenvian para su calculo y se muestran
                // en la vista
                else -> {
                    operadores = getDatos()

                    // Realizacion de calculos de la formula elegida por el usuario
                    val resultado = setOperacion(formulaSelected, operadores)
                    intent.putExtra("resultado", resultado)

                    // Se pasa a la nueva activity y se imprime el resultado
                    startActivity(intent)
                }
            }
        }
    }

    // Se sobrescriben los metodos encargados de mostrar las formulas en caso de que el usuario
    // haya seleccionado alguna o no
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        try {
            // Obtencion de la formula seleccionada mediante el spinner
            formulaSelected = spinner.getItem(position).toString()
            binding.imgFormula.setImageResource(getImageId(this, formulaSelected!!))

            // Asignacion de valor correspondiente de incognita dependiendo de la formula elegida
            setIncogita(formulaSelected.toString())
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error: No fue posible encontrar la imagen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // De forma predeterminada se establece como primer formula la de voltaje
    override fun onNothingSelected(p0: AdapterView<*>?) {
        formulaSelected = "voltaje"
        binding.imgFormula.setImageResource(getImageId(this, formulaSelected!!))
    }

    // Obtencion del id de las imagenes de cada formula
    fun getImageId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier("drawable/$imageName", null, context.packageName)
    }

    // Generacion de texto de cada incognita dependiendo de la formula elegida
    fun setIncogita(formula: String) {
        try {
            // Se verifica la formula elegida por el usuario
            with(binding) {
                when {
                    formula.equals("voltaje") -> {
                        binding.tvVal1.text = "I:"
                        binding.tvVal2.text = "R:"
                    }
                    formula.equals("corriente") -> {
                        binding.tvVal1.text = "V:"
                        binding.tvVal2.text = "R:"
                    }
                    formula.equals("resistencia") -> {
                        binding.tvVal1.text = "V:"
                        binding.tvVal2.text = "I:"
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Error: No se encontro la formula ingresada.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error: No fue posible encontrar la imagen.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}