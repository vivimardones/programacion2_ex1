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

    fun agregarItem(itemMesa: ItemMesa) {
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

// Método Main para probar la funcionalidad
fun main() {
    val item1 = ItemMenu("Hamburguesa", 5.0f)
    val item2 = ItemMenu("Papas Fritas", 3.0f)
    val item3 = ItemMenu("Coca Cola", 2.0f)

    val cuenta = CuentaMesa()
    cuenta.agregarItem(item1, 2) // 2 Hamburguesas
    cuenta.agregarItem(item2, 3) // 3 Papas Fritas
    cuenta.agregarItem(item3, 1) // 1 Coca Cola

    println("Total sin propina: ${cuenta.calcularTotalSinPropina()}")
    println("Propina: ${cuenta.calcularPropina()}")
    println("Total con propina: ${cuenta.calcularTotalConPropina()}")
}
