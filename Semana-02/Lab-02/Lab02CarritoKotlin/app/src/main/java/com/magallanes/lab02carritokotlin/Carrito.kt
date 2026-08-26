package com.magallanes.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("==")
    println("CARRITO DE COMPRAS")
    println("TIENDA TECSUP")
    println("==")
}