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


interface EstrategiaDescuento {

    fun calcularDescuento(subtotal: Double): Double
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


class DescuentoPorMonto : EstrategiaDescuento {

    override fun calcularDescuento(subtotal: Double): Double {

        return when {
            subtotal > 5000 -> subtotal * 0.10
            subtotal > 3000 -> subtotal * 0.05
            else -> 0.0
        }
    }

    fun obtenerPorcentaje(subtotal: Double): Double {

        return when {
            subtotal > 5000 -> 10.0
            subtotal > 3000 -> 5.0
            else -> 0.0
        }
    }
}


class CarritoCompras(
    private val cliente: Cliente,
    private val estrategiaDescuento: EstrategiaDescuento
) {

    private val items: MutableList<ItemCarrito> = mutableListOf()


    fun agregarProducto(producto: ProductoBase) {
        agregarProducto(producto, 1)
    }


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


    fun calcularDescuento(): Double {

        return estrategiaDescuento.calcularDescuento(
            calcularSubtotal()
        )
    }


    fun obtenerPorcentajeDescuento(): Double {

        val subtotal = calcularSubtotal()

        return when {
            subtotal > 5000 -> 10.0
            subtotal > 3000 -> 5.0
            else -> 0.0
        }
    }


    fun calcularIGV(): Double {

        val subtotalConDescuento =
            calcularSubtotal() - calcularDescuento()

        return subtotalConDescuento * 0.18
    }


    fun calcularTotal(): Double {

        val subtotal = calcularSubtotal()
        val descuento = calcularDescuento()

        val subtotalConDescuento =
            subtotal - descuento

        val igv =
            subtotalConDescuento * 0.18

        return subtotalConDescuento + igv
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

    val monitor = ProductoFisico(
        "Monitor Samsung",
        1200.00
    )

    val curso = ProductoDigital(
        "Curso Kotlin",
        800.00
    )


    val estrategiaDescuento = DescuentoPorMonto()


    val carrito = CarritoCompras(
        cliente,
        estrategiaDescuento
    )


    carrito.agregarProducto(laptop, 2)
    carrito.agregarProducto(monitor)
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
                    "S/ ${
                        String.format(
                            "%.2f",
                            item.calcularImporte()
                        )
                    }"
        )
    }


    println()
    println("Cantidad total: ${carrito.calcularCantidadTotal()}")

    println(
        "Subtotal: S/ ${
            String.format(
                "%.2f",
                carrito.calcularSubtotal()
            )
        }"
    )

    println(
        "Descuento (${carrito.obtenerPorcentajeDescuento().toInt()}%): S/ ${
            String.format(
                "%.2f",
                carrito.calcularDescuento()
            )
        }"
    )

    println(
        "IGV (18%): S/ ${
            String.format(
                "%.2f",
                carrito.calcularIGV()
            )
        }"
    )

    println(
        "TOTAL A PAGAR: S/ ${
            String.format(
                "%.2f",
                carrito.calcularTotal()
            )
        }"
    )
}