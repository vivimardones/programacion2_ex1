package cl.vivimardones.ex1_vivianamardones

import CuentaMesa
import ItemMenu
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var tvItem1: TextView ?= null // PASTEL
    private var tvItem2: TextView ?= null // CAZUELA
    private var etCantItem1: EditText ?= null // cant Pastel
    private var etCantItem2: EditText?= null // cant Cazuela
    private var tvPrecioItem1: TextView ?= null // precio Pastel
    private var tvPrecioItem2: TextView?= null // precio Cazuela

    //Se obtienen los datos totales
    private var tvTotalComida: TextView ?= null // Total Comida
    private var tvTotalPropina: TextView ?= null // Total Propina
    private var tvTotalPagar: TextView ?= null // Total a pagar

    private var stPropina: Switch ? = null // da propina


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvItem1 = findViewById(R.id.tvItem1)
        tvItem2 = findViewById(R.id.tvItem2)
        etCantItem1 = findViewById(R.id.etCantItem1)
        etCantItem2 = findViewById(R.id.etCantItem2)
        tvPrecioItem1 = findViewById(R.id.tvPrecioItem1)
        tvPrecioItem2 = findViewById(R.id.tvPrecioItem2)
        tvTotalComida = findViewById(R.id.tvTotalComidaRes)
        tvTotalPropina = findViewById(R.id.tvPropinaRes)
        tvTotalPagar = findViewById(R.id.tvTotalRes)

        stPropina = findViewById(R.id.stPropina)
        val textWatcher: TextWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                agregarMenu()
            }
        }

        stPropina?.setOnCheckedChangeListener { _, isChecked -> agregarMenu()}
        etCantItem1?.addTextChangedListener(textWatcher)
        etCantItem2?.addTextChangedListener(textWatcher)

    }

    private fun agregarMenu(){
        val mesa = CuentaMesa()
        val nombreItem1 = tvItem1?.text.toString()?:""
        val precioItem1 = tvPrecioItem1?.text.toString().toFloatOrNull()?:0.0f
        val nombreItem2 = tvItem2?.text.toString()?:""
        val precioItem2 = tvPrecioItem2?.text.toString().toFloatOrNull()?:0.0f
        val daPropina = stPropina?.isChecked
        val item1 = ItemMenu(nombreItem1, precioItem1)
        val item2 = ItemMenu(nombreItem2, precioItem2)
        val cant1 = etCantItem1?.text.toString().toIntOrNull()?:0
        val cant2 = etCantItem2?.text.toString().toIntOrNull()?:0
        mesa.agregarItem(item1,cant1)
        mesa.agregarItem(item2,cant2)
        val totalSinPropina = mesa.calcularTotalSinPropina()
        val propina = mesa.calcularPropina()
        val totalConPropina = mesa.calcularTotalConPropina()
        var total = 0f
        if(daPropina == true){
            total = totalConPropina
        } else {
             total = totalSinPropina
        }
        tvTotalComida?.setText(mesa.formatearMoneda(totalSinPropina))
        tvTotalPropina?.setText(mesa.formatearMoneda(propina))
        tvTotalPagar?.setText(mesa.formatearMoneda(total))
    }

}