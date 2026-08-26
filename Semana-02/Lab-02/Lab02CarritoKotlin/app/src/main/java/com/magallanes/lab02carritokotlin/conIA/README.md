# Carrito de Compras - Arquitectura POO (con IA)

* **Alumno:** Jose Magallanes
* **Curso:** Programación en Móviles
* **Docente:** Juan José León Suiyon

## Demostración de Pilares POO

1. **Abstracción:** Implementación de interfaces `Calculable` y `EstrategiaDescuento` para desacoplar comportamientos de la lógica concreta.
2. **Herencia:** Clase base `ProductoBase` heredada por `ProductoFisico` y `ProductoDigital`.
3. **Polimorfismo:**
    - Sobrescritura (`Overriding`): Implementación personalizada de `calcularImporte()` y `toString()`.
    - Sobrecarga (`Overloading`): Múltiples firmas para `agregarProducto()` en `CarritoCompras`.
4. **Encapsulamiento:** Ocultamiento de la lista `items` (`private val items`) y validación de atributos mutables mediante `private set`.
5. **Relaciones entre Objetos:**
    - Agregación: La entidad `Cliente` existe independientemente del carrito.
    - Composición: La clase `ItemCarrito` está ligada directamente al ciclo de vida del carrito de compras.