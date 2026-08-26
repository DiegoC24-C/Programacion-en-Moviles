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


fun main() {

    val cliente = Cliente(
        "Diego Magallanes",
        "76543210"
    )

    val laptop = ProductoFisico(
        "Laptop Lenovo",
        2500.00
    )

    val curso = ProductoDigital(
        "Curso Kotlin",
        800.00
    )

    println("CARRITO DE COMPRAS TIENDA TECSUP")
    println("Cliente: ${cliente.obtenerNombre()}")
    println("Documento: ${cliente.obtenerDocumento()}")

    println()
    println("PRODUCTOS")
    println(laptop)
    println(curso)

    println()
    println("Importe laptop: S/ ${laptop.calcularImporte()}")
    println("Importe curso: S/ ${curso.calcularImporte()}")
}