class Cliente(
    private val nombre: String,
    private val documento: String
) {

    fun obtenerNombre(): String {
        return nombre
    }

    fun obtenerDocumento(): String {
        return documento
    }

    override fun toString(): String {
        return "$nombre - DNI: $documento"
    }
}


interface Calculable {

    fun calcularImporte(): Double
}


abstract class ProductoBase(
    protected val nombre: String,
    protected val precioBase: Double
) : Calculable {

    init {
        require(nombre.isNotBlank()) {
            "El nombre no puede estar vacío."
        }

        require(precioBase >= 0) {
            "El precio no puede ser negativo."
        }
    }

    fun obtenerNombre(): String {
        return nombre
    }

    fun obtenerPrecioBase(): Double {
        return precioBase
    }

    abstract override fun calcularImporte()

    override fun toString(): String {
        return "$nombre - S/ ${String.format("%.2f", precioBase)}"
    }
}


class ProductoFisico(
    nombre: String,
    precioBase: Double
) : ProductoBase(nombre, precioBase) {

    override fun calcularImporte(): Double {
        return precioBase
    }

    override fun toString(): String {
        return "Producto físico: $nombre - S/ ${String.format("%.2f", precioBase)}"
    }
}


class ProductoDigital(
    nombre: String,
    precioBase: Double
) : ProductoBase(nombre, precioBase) {

    override fun calcularImporte(): Double {
        return precioBase * 1.02
    }

    override fun toString(): String {
        return "Producto digital: $nombre - S/ ${String.format("%.2f", precioBase)}"
    }
}


// ============================================================
// COMMIT 4 - ITEM DEL CARRITO
// ============================================================

class ItemCarrito(
    private val producto: ProductoBase,
    cantidadInicial: Int
) : Calculable {

    private var cantidad: Int = 0

    init {
        actualizarCantidad(cantidadInicial)
    }

    fun obtenerProducto(): ProductoBase {
        return producto
    }

    fun obtenerCantidad(): Int {
        return cantidad
    }

    fun actualizarCantidad(nuevaCantidad: Int) {

        require(nuevaCantidad > 0) {
            "La cantidad debe ser mayor que 0."
        }

        cantidad = nuevaCantidad
    }

    override fun calcularImporte(): Double {
        return producto.calcularImporte() * cantidad
    }

    override fun toString(): String {
        return "${producto.obtenerNombre()} x $cantidad"
    }
}


fun main() {

    val cliente = Cliente(
        "Diego Magallanes",
        "76543210"
    )

    val laptop = ProductoFisico(
        "Laptop Lenovo",
        2500.00
    )

    val curso = ProductoDigital(
        "Curso Kotlin",
        800.00
    )

    // Probamos ItemCarrito
    val itemLaptop = ItemCarrito(
        laptop,
        2
    )

    val itemCurso = ItemCarrito(
        curso,
        3
    )

    println("CARRITO DE COMPRAS TIENDA TECSUP")
    println("Cliente: ${cliente.obtenerNombre()}")
    println("Documento: ${cliente.obtenerDocumento()}")

    println()
    println("ITEMS")

    println(itemLaptop)
    println("Importe: S/ ${itemLaptop.calcularImporte()}")

    println()

    println(itemCurso)
    println("Importe: S/ ${itemCurso.calcularImporte()}")
}