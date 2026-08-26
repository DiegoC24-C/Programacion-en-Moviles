# Carrito de Compras - Arquitectura POO (con IA)

* **Alumno:** Diego Alejandro Magallanes
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
## Prompt utilizado:
Actúa como un Desarrollador Senior de Software experto en Kotlin y arquitectura limpia. Necesito refactorizar el proyecto "Carrito de Compras Tecsup" aplicando de forma estricta TODOS los fundamentos de la Programación Orientada a Objetos (POO).

Debes incluir explícitamente y justificar mediante código cada uno de los siguientes pilares:

1. ABSTRACCIÓN:
   - Crea una interfaz o clase abstracta (ej. `Calculable` o `ElementoVenta`) con métodos abstractos como `calcularImporte(): Double`.
   - Crea una interfaz o clase abstracta para la lógica de descuentos (ej. `EstrategiaDescuento`).

2. HERENCIA:
   - Crea una clase base abstracta `ProductoBase` que contenga el estado común (`nombre`, `precioBase`).
   - Hereda de ella creando subclases concretas como `ProductoFisico` o `ProductoDigital`.

3. POLIMORFISMO:
   - Sobrescribe el método `calcularImporte()` en las subclases para dar comportamientos distintos (Sobrescritura / Overriding).
   - Sobrescribe métodos estándar como `toString()` para dar una representación textual personalizada.
   - Aplica sobrecarga de métodos (Overloading) en la adición de productos al carrito.

4. ENCAPSULAMIENTO:
   - Todas las propiedades internas y listas (como los ítems dentro de `CarritoCompras`) deben ser estrictamente `private` o `protected`.
   - Modifica el estado únicamente mediante métodos mutadores con validaciones (ej. validar que la cantidad sea mayor a 0).

5. COMPOSICIÓN Y AGREGACIÓN:
   - Agregación: La clase `CarritoCompras` se relaciona con una entidad `Cliente`.
   - Composición: La clase `CarritoCompras` posee una lista interna de objetos `ItemCarrito` (que asocian un `ProductoBase` con una `cantidad`).

REQUISITOS DE SALIDA EN CONSOLA:
La ejecución en `fun main()` debe generar exactamente el reporte formateado con `String.format` que responda al flujo original de la tienda:
- Encabezado "CARRITO DE COMPRAS TIENDA TECSUP".
- Datos del Cliente.
- Confirmación de productos agregados.
- Detalle del carrito en columnas alineadas con 2 decimales (Ítem, Nombre, Cantidad, Importe).
- Resumen financiero: Cantidad total de productos, Subtotal, IGV (18%), TOTAL A PAGAR.
- Búsqueda del producto más caro mediante encapsulamiento.
- Cálculo de descuento (5% > S/3000 o 10% > S/5000) usando `when` encapsulado en una clase.
- Mensaje final de agradecimiento al cliente.

Por favor, entrega un código único, profesional, autocontenido y completamente listo para compilar en Kotlin.