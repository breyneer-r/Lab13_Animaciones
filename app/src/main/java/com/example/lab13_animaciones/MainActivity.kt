package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13_AnimacionesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 🚩 Llamada al Ejercicio 2
                    Ejercicio2_AnimateColorAsState(modifier = Modifier.padding(innerPadding))

                    // Para probar el Ejercicio 1 de nuevo, usa:
                    // Ejercicio1_AnimatedVisibility(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 🎨 Ejercicio 2: Cambio de Color con animateColorAsState
// ----------------------------------------------------

@Composable
fun Ejercicio2_AnimateColorAsState(modifier: Modifier = Modifier) {
    // 1. Variable de estado booleana para alternar el color.
    var isBlue by remember { mutableStateOf(true) }

    // Definir los colores objetivo (Azul y Verde)
    val targetColor = if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50)

    // 2. Usar animateColorAsState para animar la transición del color de forma continua.
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        // 3. Experimentación: Usa 'tween' (transición suave de 1 seg) o 'spring()' (efecto elástico).
        animationSpec = tween(durationMillis = 1000),
        label = "Color Animation"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Botón para cambiar el estado
        Button(
            onClick = { isBlue = !isBlue },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(text = "Cambiar a ${if (isBlue) "Verde" else "Azul"}")
        }

        // El cuadro que usa el color animado
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(animatedColor) // Color animado
        )
    }
}

// ----------------------------------------------------
// 🚀 Ejercicio 1: Animación de Visibilidad con AnimatedVisibility
// (Se mantiene en el archivo por si quieres probarlo de nuevo)
// ----------------------------------------------------

@Composable
fun Ejercicio1_AnimatedVisibility(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = { isVisible = !isVisible },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(text = if (isVisible) "Ocultar Cuadro" else "Mostrar Cuadro")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = spring()) + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut(animationSpec = spring()) + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFF00796B))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationPreview() {
    Lab13_AnimacionesTheme {
        // Puedes cambiar aquí la función de previsualización si lo necesitas
        Ejercicio2_AnimateColorAsState()
    }
}