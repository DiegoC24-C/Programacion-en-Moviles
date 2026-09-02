# Laboratorio 03: Registro de Producto

**Alumno:** Diego Magallanes  
**Curso:** Programación en Móviles

## Descripción
Aplicación construida en Jetpack Compose para registrar un producto calculando su importe total con dos decimales mediante gestión de estados.

## Capturas de Pantalla
- Formulario Vacío: ![Pantalla Inicial](captura1.png)
- Producto Registrado: ![Producto Registrado](captura2.png)

## Pregunta de Reflexión
**¿Qué pasaría si declaras las variables de los campos SIN `remember`?**
Si se declaran las variables sin `remember`, cada vez que el usuario escribe un carácter se desencadena una recomposición en Compose. Al recomponer la pantalla sin `remember`, las variables se reinician al valor por defecto `""`, lo que provoca que el texto ingresado se borre de inmediato y no se mantenga el estado.