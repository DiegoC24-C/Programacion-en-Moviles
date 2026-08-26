package com.magallanes.lab02carritokotlin.conIA
interface Calculable {
    fun calcularImporte(): Double
}

interface EstrategiaDescuento {
    fun calcularDescuento(montoTotal: Double): Double
}

abstract class ProductoBase(
    val nombre: String,
    val precioBase: Double
) : Calculable {
    init {
        require(precioBase >= 0.0) { "El precio no puede ser negativo" }
    }

    override fun toString(): String = String.format("%s (S/ %.2f)", nombre, precioBase)
}

class ProductoFisico(
    nombre: String,
    precioBase: Double
) : ProductoBase(nombre, precioBase) {
    override fun calcularImporte(): Double = precioBase
}

class ProductoDigital(
    nombre: String,
    precioBase: Double,
    val descuentoLicencia: Double = 0.0
) : ProductoBase(nombre, precioBase) {
    override fun calcularImporte(): Double = precioBase - descuentoLicencia
}

class DescuentoMontoTecsup : EstrategiaDescuento {
    override fun calcularDescuento(montoTotal: Double): Double {
        return when {
            montoTotal > 5000.0 -> montoTotal * 0.10
            montoTotal > 3000.0 -> montoTotal * 0.05
            else -> 0.0
        }
    }
}

class Cliente(val nombre: String)

class ItemCarrito(
    val producto: ProductoBase,
    cantidadInicial: Int
) : Calculable {
    var cantidad: Int = cantidadInicial
        private set(value) {
            require(value > 0) { "La cantidad debe ser mayor a 0" }
            field = value
        }

    fun actualizarCantidad(nuevaCantidad: Int) {
        this.cantidad = nuevaCantidad
    }

    override fun calcularImporte(): Double = producto.calcularImporte() * cantidad
}

class CarritoCompras(
    val cliente: Cliente,
    private val estrategiaDescuento: EstrategiaDescuento = DescuentoMontoTecsup()
) {
    private val items = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: ProductoBase) {
        agregarProducto(producto, 1)
    }

    fun agregarProducto(producto: ProductoBase, cantidad: Int) {
        require(cantidad > 0) { "La cantidad agregada debe ser mayor a 0" }
        val itemExistente = items.find { it.producto.nombre == producto.nombre }
        if (itemExistente != null) {
            itemExistente.actualizarCantidad(itemExistente.cantidad + cantidad)
        } else {
            items.add(ItemCarrito(producto, cantidad))
        }
        println("Producto agregado: ${producto.nombre}")
    }

    fun obtenerCantidadProductos(): Int = items.size
    fun calcularSubtotal(): Double = items.sumOf { it.calcularImporte() }
    fun calcularIGV(): Double = calcularSubtotal() * 0.18
    fun calcularTotal(): Double = calcularSubtotal() + calcularIGV()

    fun obtenerProductoMasCaro(): ProductoBase? {
        return items.maxByOrNull { it.producto.precioBase }?.producto
    }

    fun generarComprobante() {
        println("DETALLE DEL CARRITO")
        items.forEachIndexed { i, item ->
            println(
                String.format(
                    "%d. %-20s x%d S/ %8.2f",
                    i + 1,
                    item.producto.nombre,
                    item.cantidad,
                    item.calcularImporte()
                )
            )
        }

        val subtotal = calcularSubtotal()
        val igv = calcularIGV()
        val total = calcularTotal()
        val masCaro = obtenerProductoMasCaro()
        val descuento = estrategiaDescuento.calcularDescuento(total)
        val totalConDescuento = total - descuento

        println(String.format("%-23s: %d", "Cantidad de productos", obtenerCantidadProductos()))
        println(String.format("%-23s: S/ %8.2f", "Subtotal", subtotal))
        println(String.format("%-23s: S/ %8.2f", "IGV (18%)", igv))
        println(String.format("%-23s: S/ %8.2f", "TOTAL A PAGAR", total))

        if (masCaro != null) {
            println(String.format("Producto mas caro: %s (S/%.2f)", masCaro.nombre, masCaro.precioBase))
        }

        if (descuento > 0) {
            val porcentaje = if (total > 5000) "10%" else "5%"
            println("Descuento aplicado: $porcentaje por compra mayor a S/ 3000")
            println(String.format("TOTAL CON DESCUENTO : S/%.2f", totalConDescuento))
        }

        println("\nGracias por su compra, ${cliente.nombre}!")
    }
}

fun main() {
    println("==")
    println("CARRITO DE COMPRAS")
    println("TIENDA TECSUP")
    println("==")

    val cliente = Cliente("Jose Magallanes")
    val carrito = CarritoCompras(cliente)
    println("Cliente: ${cliente.nombre}\n")

    carrito.agregarProducto(ProductoFisico("Laptop HP", 2500.0), 1)
    carrito.agregarProducto(ProductoFisico("Mouse Logitech", 45.5), 2)
    carrito.agregarProducto(ProductoFisico("Audifonos Sony", 120.0), 1)
    carrito.agregarProducto(ProductoFisico("USB Kingston 64GB", 25.0), 3)

    println()
    carrito.generarComprobante()
}