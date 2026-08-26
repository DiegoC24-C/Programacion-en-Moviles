# Lab 02: Carrito de Compras en Kotlin

* **Alumno:** Diego Alejandro Magallanes
* **Curso:** Programación en Móviles
* **Docente:** Juan José León Suiyon

## Descripción
Código de consola en Kotlin para gestionar un carrito de compras simple. Usa una data class para estructurar los productos, realiza el cálculo de subtotal, IGV y total, busca el producto con mayor precio y aplica descuentos según el importe final mediante la estructura `when`.

## Preguntas de reflexión

**1. ¿Por qué `nombre` y `precio` son `val` pero `cantidad` es `var`?**
`nombre` y `precio` son `val` porque son datos fijos del catálogo que no cambian durante la compra. `cantidad` es `var` porque el cliente puede modificar cuántas unidades lleva de cada ítem en el carrito.

**2. ¿Qué pasa si intentas cambiar el precio después de crear el producto?**
Kotlin no te deja compilar el proyecto y muestra el error `Val cannot be reassigned`, ya que los valores declarados con `val` son inmutables.