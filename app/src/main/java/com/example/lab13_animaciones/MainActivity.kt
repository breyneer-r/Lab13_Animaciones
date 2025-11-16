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
import androidx.compose.animation.core.animateDpAsState // Importante para Ejercicio 3
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset // Importante para Ejercicio 3
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
                    // 🚩 Llamada al Ejercicio 3
                    Ejercicio3_AnimateDpAsState(modifier = Modifier.padding(innerPadding))

                    // Para probar otros ejercicios, descomenta el que necesites:
                    // Ejercicio1_AnimatedVisibility(modifier = Modifier.padding(innerPadding))
                    // Ejercicio2_AnimateColorAsState(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 📏 Ejercicio 3: Animación de Tamaño y Posición (animateDpAsState)
// ----------------------------------------------------

@Composable
fun Ejercicio3_AnimateDpAsState(modifier: Modifier = Modifier) {
    // 1. Variable de estado para alternar la posición y el tamaño
    var isLarge by remember { mutableStateOf(false) }

    // Definir los valores objetivo (en Dp)
    val targetSize = if (isLarge) 250.dp else 100.dp
    // Desplazamiento de 100 dp en X e Y
    val targetOffset = if (isLarge) 100.dp else 0.dp

    // 2. Usar animateDpAsState para animar el tamaño
    val animatedSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = tween(durationMillis = 800),
        label = "Size Animation"
    )

    // Usar animateDpAsState para animar el desplazamiento (posición)
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 800),
        label = "Offset Animation"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = { isLarge = !isLarge },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(text = if (isLarge) "Restablecer" else "Agrandar y Mover")
        }

        // 3. Aplicar los modificadores animados.
        // El orden es importante: primero la posición y luego el tamaño/fondo.
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = animatedOffset) // Posición animada
                .size(animatedSize) // Tamaño animado
                .background(Color(0xFFE91E63)) // Color rosado
        )
    }
}

// ----------------------------------------------------
// 🎨 Ejercicio 2: Cambio de Color con animateColorAsState
// ----------------------------------------------------

@Composable
fun Ejercicio2_AnimateColorAsState(modifier: Modifier = Modifier) {
    var isBlue by remember { mutableStateOf(true) }
    val targetColor = if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50)
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
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
        Button(
            onClick = { isBlue = !isBlue },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(text = "Cambiar a ${if (isBlue) "Verde" else "Azul"}")
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(animatedColor)
        )
    }
}

// ----------------------------------------------------
// 🚀 Ejercicio 1: Animación de Visibilidad con AnimatedVisibility
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
        Ejercicio3_AnimateDpAsState()
    }
}