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


// ============================================================
// COMMIT 5 - CARRITO DE COMPRAS
// ============================================================

class CarritoCompras(
    private val cliente: Cliente
) {

    private val items: MutableList<ItemCarrito> = mutableListOf()

    fun agregarProducto(
        producto: ProductoBase,
        cantidad: Int
    ) {

        require(cantidad > 0) {
            "La cantidad debe ser mayor que 0."
        }

        val nuevoItem = ItemCarrito(
            producto,
            cantidad
        )

        items.add(nuevoItem)
    }

    fun obtenerItems(): List<ItemCarrito> {
        return items.toList()
    }

    fun obtenerCliente(): Cliente {
        return cliente
    }

    fun calcularSubtotal(): Double {

        return items.sumOf {
            it.calcularImporte()
        }
    }

    fun calcularCantidadTotal(): Int {

        return items.sumOf {
            it.obtenerCantidad()
        }
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

    val carrito = CarritoCompras(cliente)

    carrito.agregarProducto(laptop, 2)
    carrito.agregarProducto(curso, 3)

    println("CARRITO DE COMPRAS TIENDA TECSUP")
    println("Cliente: ${carrito.obtenerCliente().obtenerNombre()}")
    println("Documento: ${carrito.obtenerCliente().obtenerDocumento()}")

    println()
    println("DETALLE DEL CARRITO")

    carrito.obtenerItems().forEach { item ->

        println(
            "${item.obtenerProducto().obtenerNombre()} " +
                    "x ${item.obtenerCantidad()} = " +
                    "S/ ${item.calcularImporte()}"
        )
    }

    println()
    println("Cantidad total: ${carrito.calcularCantidadTotal()}")
    println("Subtotal: S/ ${carrito.calcularSubtotal()}")
}