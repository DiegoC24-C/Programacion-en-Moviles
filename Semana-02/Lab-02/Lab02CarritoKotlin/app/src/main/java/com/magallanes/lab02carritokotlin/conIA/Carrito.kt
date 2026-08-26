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

        val itemExistente = items.find {
            it.obtenerProducto().obtenerNombre() ==
                    producto.obtenerNombre()
        }

        if (itemExistente != null) {

            itemExistente.actualizarCantidad(
                itemExistente.obtenerCantidad() + cantidad
            )

        } else {

            items.add(
                ItemCarrito(
                    producto,
                    cantidad
                )
            )
        }
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


    fun obtenerProductoMasCaro(): ProductoBase? {

        return items
            .maxByOrNull {
                it.obtenerProducto().obtenerPrecioBase()
            }
            ?.obtenerProducto()
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

    val teclado = ProductoFisico(
        "Teclado Mecánico",
        350.00
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


    // ========================================================
    // PRODUCTOS AGREGADOS
    // ========================================================

    carrito.agregarProducto(laptop, 1)
    carrito.agregarProducto(monitor, 2)
    carrito.agregarProducto(teclado, 2)
    carrito.agregarProducto(curso)


    // ========================================================
    // DATOS PARA EL REPORTE
    // ========================================================

    val subtotal = carrito.calcularSubtotal()
    val descuento = carrito.calcularDescuento()
    val porcentajeDescuento = carrito.obtenerPorcentajeDescuento()
    val igv = carrito.calcularIGV()
    val total = carrito.calcularTotal()


    // ========================================================
    // REPORTE
    // ========================================================

    println(
        String.format(
            """
            ================================================================
                    CARRITO DE COMPRAS TIENDA TECSUP
            ================================================================
            
            CLIENTE
            Nombre    : %s
            Documento : %s
            
            ------------------------------------------------
            PRODUCTOS AGREGADOS
            ------------------------------------------------
            """.trimIndent(),
            cliente.obtenerNombre(),
            cliente.obtenerDocumento()
        )
    )


    carrito.obtenerItems().forEach { item ->

        println(
            String.format(
                "✓ %s x%d",
                item.obtenerProducto().obtenerNombre(),
                item.obtenerCantidad()
            )
        )
    }


    println()

    println(
        String.format(
            """
            ------------------------------------------------
            DETALLE DEL CARRITO
            ------------------------------------------------
            %-5s %-25s %10s %15s
            ------------------------------------------------
            """.trimIndent(),
            "Ítem",
            "Nombre",
            "Cantidad",
            "Importe"
        )
    )


    carrito.obtenerItems().forEachIndexed { indice, item ->

        println(
            String.format(
                "%-5d %-25s %10d %15.2f",
                indice + 1,
                item.obtenerProducto().obtenerNombre(),
                item.obtenerCantidad(),
                item.calcularImporte()
            )
        )
    }


    println(
        String.format(
            """
            
            ------------------------------------------------
            RESUMEN FINANCIERO
            ------------------------------------------------
            Cantidad total de productos : %d
            Subtotal                    : S/ %10.2f
            Descuento (%2.0f%%)             : S/ %10.2f
            IGV (18%%)                   : S/ %10.2f
            ------------------------------------------------
            TOTAL A PAGAR              : S/ %10.2f
            ------------------------------------------------
            """.trimIndent(),
            carrito.calcularCantidadTotal(),
            subtotal,
            porcentajeDescuento,
            descuento,
            igv,
            total
        )
    )


    val productoMasCaro =
        carrito.obtenerProductoMasCaro()


    println(
        String.format(
            "Producto más caro            : %s - S/ %.2f",
            productoMasCaro?.obtenerNombre() ?: "No disponible",
            productoMasCaro?.obtenerPrecioBase() ?: 0.0
        )
    )


    println()

    println(
        String.format(
            "Gracias por su compra, %s. ¡Vuelva pronto a Tienda Tecsup!",
            cliente.obtenerNombre()
        )
    )

    println(
        "================================================================"
    )
}