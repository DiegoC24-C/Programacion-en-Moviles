package com.magallanes.teoria02app

import java.util.Scanner
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val scanner = Scanner(System.`in`)
    val formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    println("--- SISTEMA DE CONTROL DE MULTAS DE BIBLIOTECA ---")

    print("Ingrese el título del libro: ")
    val tituloLibro = scanner.nextLine()

    println("\nSeleccione tipo de usuario:")
    println("1. Docente (S/ 3.00/día)")
    println("2. Alumno (S/ 1.50/día)")
    print("Opción (1 o 2): ")
    val opcion = scanner.nextInt()
    scanner.nextLine()

    val tipoUsuario = if (opcion == 1) "Docente" else "Alumno"
    val tarifaDiaria = if (opcion == 1) 3.00 else 1.50

    print("Ingrese Fecha de Préstamo (dd/MM/yyyy): ")
    val fechaPrestamo = LocalDate.parse(scanner.nextLine(), formatoEntrada)

    print("Ingrese Fecha Pactada de Devolución (dd/MM/yyyy): ")
    val fechaDevolucionPactada = LocalDate.parse(scanner.nextLine(), formatoEntrada)

    print("Ingrese Fecha Real de Entrega (dd/MM/yyyy): ")
    val fechaEntregaReal = LocalDate.parse(scanner.nextLine(), formatoEntrada)
}