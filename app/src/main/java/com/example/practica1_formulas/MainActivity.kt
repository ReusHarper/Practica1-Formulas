package com.example.practica1_formulas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.practica1_formulas.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    // Se establece un objeto de tipo binding para acceder a todos los elementos
    // de la vista
    private lateinit var binding: ActivityMainBinding

    // Se establecen los elementos encargadados de almancenar los valores que \
    // ingresara el usuario junto con las formulas disponibles
    private lateinit var operadores: ArrayList<Double>
    private lateinit var spinner: ArrayAdapter<String>
    private lateinit var formulaSelected: String
    private lateinit var unidades: String
    private var error: Boolean = true

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

    // Para este ejercicio se realizara cada una de las operaciones por separado, teniendo en cuenta
    // sus metodos de forma independiente
    fun voltaje(operadores: ArrayList<Double>): Double {
        var resultado = 0.0

        try {
            error = false
            resultado = String.format("%.2f", (operadores[0] * operadores[1]) ).toDouble()
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
            // Se verifica que el dividendo sea diferente de cero
            if (operadores[1] != 0.0) {
                error = false
                resultado = String.format("%.2f", (operadores[0] / operadores[1]) ).toDouble()
            }
            else {
                error = true
                alertaCero("corriente")
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
            // Se verifica que el dividendo sea diferente de cero
            if (operadores[1] != 0.0) {
                error = false
                resultado = String.format("%.2f", (operadores[0] / operadores[1]) ).toDouble()
            }
            else {
                error = true
                alertaCero("corriente")
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
                else -> {
                    operadores = getDatos()

                    // Realizacion de calculos de la formula elegida por el usuario
                    val resultado = setOperacion(formulaSelected, operadores)

                    // Si los datos ingresados son correctos se reenvian para su calculo y se muestran
                    // en la vista
                    if (!error) {
                        intent.putExtra("resultado", resultado.toString())
                        intent.putExtra("unidades", unidades)

                        // Se pasa a la nueva activity, se imprime el resultado y se finaliza la vista
                        startActivity(intent)
                        finish()
                    }
                    else {
                        // Se envia un mensaje de alerta senalando el dato ingresado que es erroneo
                        when {
                            unidades.equals("A") -> alertaCero("resistencia")
                            else -> alertaCero("corriente")
                        }
                    }
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
        unidades = "V"
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
                        unidades = "V"
                    }
                    formula.equals("corriente") -> {
                        binding.tvVal1.text = "V:"
                        binding.tvVal2.text = "R:"
                        unidades = "A"
                    }
                    formula.equals("resistencia") -> {
                        binding.tvVal1.text = "V:"
                        binding.tvVal2.text = "I:"
                        unidades = "Ohms"
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

    // Se crea un metodo encargado de enviar una alerta si el valor de la corriente o resistencia es cero
    fun alertaCero(tipo: String) {
        Toast.makeText(
            this,
            "Error: El valor de la $tipo no puede ser cero. Por favor vuelva a ingresar el dato de forma correcta.",
            Toast.LENGTH_SHORT
        ).show()
    }

}