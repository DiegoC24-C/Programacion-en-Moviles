package com.magallanes.lab03registronotas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaRegistroNotas()
                }
            }
        }
    }
}

@Composable
fun PantallaRegistroNotas() {
    var notaFundamentos by remember { mutableFloatStateOf(0f) }
    var notaPoo by remember { mutableFloatStateOf(0f) }
    var notaMoviles by remember { mutableFloatStateOf(0f) }
    var notaBd by remember { mutableFloatStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }

    var promedioPonderadoCalculado by remember { mutableStateOf<Float?>(null) }
    var promedioFinalCalculado by remember { mutableStateOf<Float?>(null) }
    var fueRedondeado by remember { mutableStateOf(false) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEDE7F6),
            Color(0xFFF3E5F5),
            Color.White
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Surface(
            color = Color(0xFF673AB7),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Registro de Notas",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Notas del ciclo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Desliza para asignar cada nota (0 a 20)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                CursoItem(
                    nombre = "Fundamentos de Programación",
                    peso = 20,
                    nota = notaFundamentos,
                    onNotaChange = { notaFundamentos = it }
                )

                CursoItem(
                    nombre = "Programación Orientada a Objetos",
                    peso = 25,
                    nota = notaPoo,
                    onNotaChange = { notaPoo = it }
                )

                CursoItem(
                    nombre = "Programación en Móviles",
                    peso = 30,
                    nota = notaMoviles,
                    onNotaChange = { notaMoviles = it }
                )

                CursoItem(
                    nombre = "Base de Datos",
                    peso = 25,
                    nota = notaBd,
                    onNotaChange = { notaBd = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Redondear promedio final", fontSize = 15.sp)
                    Switch(
                        checked = redondear,
                        onCheckedChange = { redondear = it }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = confirmado,
                        onCheckedChange = { confirmado = it }
                    )
                    Text("Confirmo que las notas son correctas", fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val ponderado = (notaFundamentos * 0.20f) +
                                (notaPoo * 0.25f) +
                                (notaMoviles * 0.30f) +
                                (notaBd * 0.25f)

                        promedioPonderadoCalculado = ponderado
                        fueRedondeado = redondear

                        promedioFinalCalculado = if (redondear) {
                            ponderado.roundToInt().toFloat()
                        } else {
                            ponderado
                        }
                    },
                    enabled = confirmado,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF673AB7),
                        disabledContainerColor = Color(0xFFC2B7E0)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "CALCULAR PROMEDIO",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                //logica del calculo
                if (promedioPonderadoCalculado == null || promedioFinalCalculado == null) {
                    Text(
                        text = "Asigna las notas y confirma para calcular",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                } else {
                    val pPonderado = promedioPonderadoCalculado!!
                    val pFinal = promedioFinalCalculado!!

                    val (observacion, colorFondoChip, colorTextoChip) = when {
                        pFinal >= 17.0f -> Triple("EXCELENTE", Color(0xFF1B5E20), Color.White)
                        pFinal >= 13.0f -> Triple("APROBADO", Color(0xFFE8F5E9), Color(0xFF2E7D32))
                        pFinal >= 10.0f -> Triple("EN RECUPERACIÓN", Color(0xFFFFF8E1), Color(0xFFE65100))
                        else -> Triple("DESAPROBADO", Color(0xFFFFEBEE), Color(0xFFC62828))
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Promedio ponderado:  ", fontSize = 16.sp)
                                Text(
                                    text = String.format("%.2f", pPonderado),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Promedio final:  ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF512DA8)
                                )
                                Text(
                                    text = if (fueRedondeado) "${pFinal.toInt()}" else String.format("%.2f", pFinal),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF512DA8)
                                )
                            }

                            if (fueRedondeado) {
                                Text(
                                    text = "(redondeado)",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Surface(
                                color = colorFondoChip,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = observacion,
                                    color = colorTextoChip,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "✓ Promedio calculated correctamente",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Desarrollado por: Diego Magallanes",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun CursoItem(
    nombre: String,
    peso: Int,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "($peso%)",
                    color = Color(0xFF7E57C2),
                    fontSize = 13.sp
                )
            }

            Surface(
                color = Color(0xFFEDE7F6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${nota.toInt()}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF512DA8),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 14.sp
                )
            }
        }

        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF512DA8),
                activeTrackColor = Color(0xFF7E57C2),
                inactiveTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}