package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.spring
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
                    // 🚩 Llamada al Ejercicio 1
                    Ejercicio1_AnimatedVisibility(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 🚀 Ejercicio 1: Animación de Visibilidad con AnimatedVisibility
// Permite mostrar u ocultar un elemento componible con efectos de entrada y salida
// ----------------------------------------------------

@Composable
fun Ejercicio1_AnimatedVisibility(modifier: Modifier = Modifier) {
    // 1. Variable de estado booleana para controlar la visibilidad.
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 2. Botón que al hacer clic alterne la visibilidad del elemento
        Button(
            onClick = { isVisible = !isVisible },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(text = if (isVisible) "Ocultar Cuadro" else "Mostrar Cuadro")
        }

        // 3. Usa AnimatedVisibility para que el cuadro aparezca y desaparezca suavemente.
        AnimatedVisibility(
            visible = isVisible,
            // 4. Configura las animaciones de entrada y salida con efectos como fadeIn y fadeOut.
            enter = fadeIn(animationSpec = spring()) + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut(animationSpec = spring()) + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            // El elemento componible (un cuadro de color)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFF00796B)) // Un color verde azulado
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AnimationPreview() {
    Lab13_AnimacionesTheme {
        Ejercicio1_AnimatedVisibility()
    }
}