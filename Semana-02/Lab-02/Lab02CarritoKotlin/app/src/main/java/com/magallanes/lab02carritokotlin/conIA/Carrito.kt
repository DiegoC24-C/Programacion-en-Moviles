package com.magallanes.lab02carritokotlin.conIA

interface Calculable {
    fun calcularImporte(): Double
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

fun main() {
    println("==")
    println("CARRITO DE COMPRAS - POO")
    println("TIENDA TECSUP")
    println("==")
}