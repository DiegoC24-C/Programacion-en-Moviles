package com.magallanes.lab02carritokotlin.sinIA

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18
fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

fun mostrarDetalle(productos: List<Producto>) {
    println("DETALLE DEL CARRITO")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun main() {
    println("==")
    println("CARRITO DE COMPRAS")
    println("TIENDA TECSUP")
    println("==")

    val nombreCliente = "Jose Magallanes"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente\n")

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Audifonos Sony", 120.0, 1))
    carrito.add(Producto("USB Kingston 64GB", 25.0, 3))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    println()

    mostrarDetalle(carrito)

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("%-23s: %d", "Cantidad de productos", carrito.size))
    println(String.format("%-23s: S/ %8.2f", "Subtotal", subtotal))
    println(String.format("%-23s: S/ %8.2f", "IGV (18%)", igv))
    println(String.format("%-23s: S/ %8.2f", "TOTAL A PAGAR", total))

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println(String.format("Producto mas caro: %s (S/%.2f)", masCaro.nombre, masCaro.precio))
    }

    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    if (descuento > 0) {
        val porcentaje = if (total > 5000) "10%" else "5%"
        println("Descuento aplicado: $porcentaje por compra mayor a S/ 3000")
        println(String.format("TOTAL CON DESCUENTO : S/%.2f", totalConDescuento))
    }

    println("\nGracias por su compra, $nombreCliente!")
}