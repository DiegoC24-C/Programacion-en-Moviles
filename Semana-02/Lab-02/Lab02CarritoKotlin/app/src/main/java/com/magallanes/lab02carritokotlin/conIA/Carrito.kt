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
}

fun main() {
    println("==")
    println("CARRITO DE COMPRAS - POO")
    println("TIENDA TECSUP")
    println("==")
}