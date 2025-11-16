package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

// Imports y Modificadores para el Ejercicio Final
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import kotlin.random.Random

// Enum para Ejercicio 4
enum class ContentState {
    LOADING, CONTENT, ERROR
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13_AnimacionesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 🚩 Llamada al EJERCICIO FINAL
                    EjercicioFinal_PrototipoVideojuego(modifier = Modifier.padding(innerPadding))

                    // Para probar otros ejercicios, descomenta el que necesites:
                    // Ejercicio1_AnimatedVisibility(modifier = Modifier.padding(innerPadding))
                    // Ejercicio2_AnimateColorAsState(modifier = Modifier.padding(innerPadding))
                    // Ejercicio3_AnimateDpAsState(modifier = Modifier.padding(innerPadding))
                    // Ejercicio4_AnimatedContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 🎮 Ejercicio Final: Prototipo de Animación de Videojuego
// Combinando animateDpAsState, animateColorAsState y AnimatedVisibility
// ----------------------------------------------------

@Composable
fun EjercicioFinal_PrototipoVideojuego(modifier: Modifier = Modifier) {
    // 1. Variables de Estado
    var isAttacking by remember { mutableStateOf(false) } // Ataque (Controla AnimatedVisibility)
    var isRight by remember { mutableStateOf(true) }      // Posición (Controla animateDpAsState)
    var healthLevel by remember { mutableStateOf(100) }   // Salud (Controla animateColorAsState)

    // 2. Animación de Posición (animateDpAsState)
    val targetPositionX: Dp = if (isRight) 200.dp else 50.dp
    val animatedPositionX by animateDpAsState(
        targetValue = targetPositionX,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f),
        label = "Player Movement"
    )

    // 3. Animación de Color (animateColorAsState)
    val targetColor = when {
        healthLevel > 60 -> Color(0xFF4CAF50) // Verde
        healthLevel > 30 -> Color(0xFFFFC107) // Amarillo
        else -> Color(0xFFF44336) // Rojo (Baja salud)
    }
    val animatedHealthColor by animateColorAsState(targetColor, label = "Health Color")

    // Lógica para simular movimiento, ataque y daño
    val performAction = {
        // Al atacar, mostramos el efecto y reducimos la salud aleatoriamente
        isAttacking = true
        healthLevel = Random.nextInt(0, 101) // Nueva salud aleatoria
        isRight = !isRight // Mover al personaje a la otra posición

        // Alternar el estado de ataque (para que el botón también funcione como restablecer el ataque visual)
        isAttacking = !isAttacking
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Marcador de Salud
        Text("HP: $healthLevel", color = animatedHealthColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Contenedor de la acción de juego
        Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            // Personaje Principal (Combina Posición y Color Animados)
            Box(
                modifier = Modifier
                    .offset(x = animatedPositionX) // Posición X animada
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(animatedHealthColor) // Color animado (salud)
                    .border(2.dp, Color.Black, CircleShape)
                    .clickable(onClick = performAction) // El personaje es interactivo
                    .align(Alignment.TopStart)
            ) {
                Text("P", Modifier.align(Alignment.Center), fontSize = 30.sp, color = Color.White)
            }

            // Efecto de Ataque (AnimatedVisibility)
            AnimatedVisibility(
                visible = isAttacking,
                enter = fadeIn(tween(durationMillis = 150)) + expandVertically(),
                exit = fadeOut(tween(durationMillis = 400)),
                modifier = Modifier
                    .offset(x = animatedPositionX + 80.dp, y = 10.dp)
                    .align(Alignment.TopStart)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .background(Color(0xFFFF5722).copy(alpha = 0.7f))
                        .clip(CircleShape)
                ) {
                    Text("¡HIT!", Modifier.align(Alignment.Center), color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Button(onClick = performAction) {
            Text("Mover/Atacar y Recibir Daño")
        }
    }
}


// ----------------------------------------------------
// 🔄 Ejercicio 4: Cambio de Contenido con AnimatedContent
// ----------------------------------------------------

@Composable
fun Ejercicio4_AnimatedContent(modifier: Modifier = Modifier) {
    var currentState by remember { mutableStateOf(ContentState.LOADING) }
    val nextState = {
        currentState = when (currentState) {
            ContentState.LOADING -> ContentState.CONTENT
            ContentState.CONTENT -> ContentState.ERROR
            ContentState.ERROR -> ContentState.LOADING
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = nextState, modifier = Modifier.padding(bottom = 32.dp)) {
            Text(text = "Cambiar Estado (Actual: $currentState)")
        }

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                (slideInVertically(animationSpec = tween(600)) { height -> height } + fadeIn(tween(600)))
                    .togetherWith(
                        slideOutVertically(animationSpec = tween(600)) { height -> -height } + fadeOut(tween(600))
                    )
            },
            label = "State Change Content"
        ) { target ->
            when (target) {
                ContentState.LOADING -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                        Spacer(Modifier.height(16.dp))
                        Text("Cargando datos...", fontSize = 20.sp)
                    }
                }
                ContentState.CONTENT -> {
                    Text("¡Contenido cargado exitosamente!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                }
                ContentState.ERROR -> {
                    Text("⚠️ Error al obtener datos. Intente de nuevo.", fontSize = 22.sp, color = Color.Red)
                }
            }
        }
    }
}


// ----------------------------------------------------
// 📏 Ejercicio 3: Animación de Tamaño y Posición
// ----------------------------------------------------

@Composable
fun Ejercicio3_AnimateDpAsState(modifier: Modifier = Modifier) {
    var isLarge by remember { mutableStateOf(false) }
    val targetSize = if (isLarge) 250.dp else 100.dp
    val targetOffset = if (isLarge) 100.dp else 0.dp

    val animatedSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = tween(durationMillis = 800),
        label = "Size Animation"
    )
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 800),
        label = "Offset Animation"
    )

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { isLarge = !isLarge }, modifier = Modifier.padding(bottom = 32.dp)) {
            Text(text = if (isLarge) "Restablecer" else "Agrandar y Mover")
        }
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = animatedOffset)
                .size(animatedSize)
                .background(Color(0xFFE91E63))
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
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { isBlue = !isBlue }, modifier = Modifier.padding(bottom = 32.dp)) {
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
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { isVisible = !isVisible }, modifier = Modifier.padding(bottom = 32.dp)) {
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
        EjercicioFinal_PrototipoVideojuego()
    }
}