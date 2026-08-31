package com.magallanes.teoria02app

import java.util.Scanner
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    val diasAtraso = ChronoUnit.DAYS.between(fechaDevolucionPactada, fechaEntregaReal)

    println("\n==================================================")
    println("LIBRO           : $tituloLibro")
    println("TIPO USUARIO    : $tipoUsuario (S/ %.2f diario)".format(tarifaDiaria))
    println("FECHA PRÉSTAMO  : ${fechaPrestamo.format(formatoEntrada)}")
    println("FECHA DEVOLUCIÓN: ${fechaDevolucionPactada.format(formatoEntrada)}")
    println("FECHA ENTREGA   : ${fechaEntregaReal.format(formatoEntrada)}")

    if (diasAtraso <= 0) {
        println("ESTADO          : Devuelto a tiempo (Sin multa)")
        println("==================================================")
        return
    }

    println("ESTADO          : Devuelto con $diasAtraso días de atraso")
    println("==================================================")

    println("%-5s %-10s %-12s %-10s".format("Día", "Fecha", "Multa Día", "Acumulado"))
    println("--------------------------------------------------")

    var acumulado = 0.0
    val formatoDiaMes = DateTimeFormatter.ofPattern("dd/MM")

    for (i in 1..diasAtraso) {
        val fechaDiaMulta = fechaDevolucionPactada.plusDays(i)
        acumulado += tarifaDiaria

        println(
            "%-5d %-10s S/ %-9.2f S/ %-8.2f".format(
                i,
                fechaDiaMulta.format(formatoDiaMes),
                tarifaDiaria,
                acumulado
            )
        )
    }
}