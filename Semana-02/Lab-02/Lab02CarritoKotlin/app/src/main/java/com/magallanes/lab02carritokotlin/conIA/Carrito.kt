package com.magallanes.lab02carritokotlin.conIA

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

fun main() {

    val cliente = Cliente(
        "Diego Magallanes",
        "76543210"
    )

    println("CARRITO DE COMPRAS TIENDA TECSUP")
    println("Cliente: ${cliente.obtenerNombre()}")
    println("Documento: ${cliente.obtenerDocumento()}")
}
interface Calculable {
    fun calcularImporte(): Double
}

abstract class ProductoBase(
    protected val nombre: String,
    protected val precioBase: Double
) : Calculable {

    init {
        require(nombre.isNotBlank())
        require(precioBase >= 0)
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