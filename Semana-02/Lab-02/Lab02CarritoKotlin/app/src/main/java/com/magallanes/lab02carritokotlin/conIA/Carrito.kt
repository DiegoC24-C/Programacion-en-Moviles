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

fun main() {
    println("==")
    println("CARRITO DE COMPRAS - POO")
    println("TIENDA TECSUP")
    println("==")
}