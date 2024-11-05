package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad principal de la app de calculadora.
 * Esta clase gestiona las operaciones básicas: suma, resta, multiplicación y división.
 * Los resultados y las entradas actuales se muestran en dos TextViews: `tv_num1` para la entrada
 * del primer operando (el número de N dígitos que introducí hasta hacer click en un operador) y
 * `tv_num2` (para la entrada actual del segundo operando y, una vez haga click en el botón '=',
 * el resultado.
 */
class MainActivity : AppCompatActivity() {

    // Códigos de operación: 0 -> Ninguno (por defecto), 1 -> suma, 2 -> resta, 3 -> multiplicación, 4 -> división
    private var num2: String = "" // Almacena la entrada actual como cadena para mostrarla en tiempo real
    private var op: Int = 0       // Almacena la operación seleccionada
    private var numero1: Double = 0.0 // Almacena el primer operando
    lateinit var tv_num1: TextView // Muestra el primer operando y el operador seleccionado
    lateinit var tv_num2: TextView // Muestra la entrada actual o el resultado

    /**
     * Inicializa la actividad, configura los enlaces a las vistas y los listeners de los botones.
     *
     * @param savedInstanceState Si es no nulo, esta actividad se está re-inicializando después de
     * haber sido cerrada anteriormente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tv_num1 = findViewById(R.id.tv_num1)
        tv_num2 = findViewById(R.id.tv_num2)
        val btnLimpiar: Button = findViewById(R.id.btn_clear)
        val btnBorra: Button = findViewById(R.id.btn_borrar)
        val btnPorc: Button = findViewById(R.id.btn_div100)
        val btnResult: Button = findViewById(R.id.btn_result)

        // Listener para el botón de resultado; realiza la operación seleccionada y muestra el resultado
        btnResult.setOnClickListener {
            val numero2: Double = tv_num2.text.toString().toDouble()
            var result: Double = 0.0

            when (op) {
                1 -> result = numero1 + numero2
                2 -> result = numero1 - numero2
                3 -> result = numero1 * numero2
                4 -> result = if (numero2 != 0.0) numero1 / numero2 else Double.NaN
            }

            tv_num2.text = result.toString()
            tv_num1.text = ""
            num2 = ""
        }

        // Listener para el botón de limpiar; reinicia la pantalla y los valores almacenados
        btnLimpiar.setOnClickListener {
            tv_num1.text = ""
            tv_num2.text = ""
            numero1 = 0.0
            num2 = ""
            op = 0
        }

        // Listener para el botón de retroceso; elimina el último carácter de la entrada actual
        btnBorra.setOnClickListener {
            var temp: String = tv_num2.text.toString()
            if (temp.isNotEmpty()) {
                temp = temp.substring(0, temp.length - 1)
                tv_num2.text = temp
                num2 = temp
            }
        }

        // Listener para el botón de porcentaje; divide la entrada actual entre 100
        btnPorc.setOnClickListener {
            val temp: String = tv_num2.text.toString()
            if (temp.isNotEmpty()) {
                tv_num2.text = (temp.toDouble() / 100).toString()
                num2 = tv_num2.text.toString()
            }
        }
    }

    /**
     * Actualiza el contenido de `tv_num2` (la pantalla inferior), agregando al valor existente el
     * del botón presionado para mostrar la entrada en tiempo real.
     *
     * @param buttonValue El valor del botón que se ha pulsado, representado como una cadena de texto.
     *
     */
    fun updateDisplay(buttonValue: String) {
        num2 += buttonValue
        tv_num2.text = num2
    }

    /**
     * Maneja las pulsaciones de los botones de dígitos, actualizando la pantalla con el dígito correspondiente.
     * También agrega un punto decimal si se pulsa, siempre que no esté presente en la entrada actual.
     *
     * @param view La vista del botón que activó este método, correspondiente a un botón numérico o de punto decimal.
     */
    fun presionarDigito(view: View) {
        when (view.id) {
            R.id.btn_num0 -> updateDisplay("0")
            R.id.btn_num1 -> updateDisplay("1")
            R.id.btn_num2 -> updateDisplay("2")
            R.id.btn_num3 -> updateDisplay("3")
            R.id.btn_num4 -> updateDisplay("4")
            R.id.btn_num5 -> updateDisplay("5")
            R.id.btn_num6 -> updateDisplay("6")
            R.id.btn_num7 -> updateDisplay("7")
            R.id.btn_num8 -> updateDisplay("8")
            R.id.btn_num9 -> updateDisplay("9")
            R.id.btn_decimal -> {
                if (!num2.contains(".")) {
                    updateDisplay(if (num2.isEmpty()) "0." else ".")
                }
            }
        }
    }

    /**
     * Maneja las pulsaciones de los botones de operación (suma, resta, multiplicación, división).
     * Almacena el primer operando y la operación seleccionada para el cálculo futuro.
     *
     * @param view La vista del botón que activó este método, correspondiente a un botón de operación.
     */
    fun clicOperacion(view: View) {
        if (tv_num2.text.isNotEmpty()) {
            numero1 = tv_num2.text.toString().toDouble()
            val num2_text: String = tv_num2.text.toString()
            tv_num2.text = ""
            num2 = ""

            when (view.id) {
                R.id.btn_suma -> {
                    tv_num1.text = "$num2_text +"
                    op = 1
                }
                R.id.btn_resta -> {
                    tv_num1.text = "$num2_text -"
                    op = 2
                }
                R.id.btn_mult -> {
                    tv_num1.text = "$num2_text *"
                    op = 3
                }
                R.id.btn_div -> {
                    tv_num1.text = "$num2_text /"
                    op = 4
                }
            }
        }
    }
}
