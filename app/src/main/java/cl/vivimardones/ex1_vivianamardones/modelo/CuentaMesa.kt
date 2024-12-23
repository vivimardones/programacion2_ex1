import java.text.NumberFormat
import java.util.Locale

// Clase ItemMenu
class ItemMenu(val nombre: String, val precio: Float)

// Clase ItemMesa
class ItemMesa(val itemMenu: ItemMenu, val cantidad: Int) {
    fun calcularSubtotal(): Float {
        return itemMenu.precio * cantidad
    }
}

// Clase CuentaMesa
class CuentaMesa() {
    private val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Float {
        return items.sumByDouble { it.calcularSubtotal().toDouble() }.toFloat()
    }

    fun calcularPropina(): Float {
        return if (aceptaPropina) calcularTotalSinPropina() * 0.1f else 0.0f
    }

    fun calcularTotalConPropina(): Float {
        return calcularTotalSinPropina() + calcularPropina()
    }

    fun formatearMoneda(valor: Float): String {
        val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return formatoCLP.format(valor)
    }
}