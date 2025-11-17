package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme
import kotlin.random.Random

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
                    EjercicioFinal_PrototipoVideojuego(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// 🎮 PROTOTIPO VIDEOJUEGO
// ----------------------------------------------------

@Composable
fun EjercicioFinal_PrototipoVideojuego(modifier: Modifier = Modifier) {

    var isAttacking by remember { mutableStateOf(false) }
    var isRight by remember { mutableStateOf(true) }
    var healthLevel by remember { mutableStateOf(100) }

    val targetPositionX: Dp = if (isRight) 200.dp else 50.dp
    val animatedPositionX by animateDpAsState(
        targetValue = targetPositionX,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f),
        label = "Player Movement"
    )

    val targetColor = when {
        healthLevel > 60 -> Color(0xFF4CAF50)
        healthLevel > 30 -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }
    val animatedHealthColor by animateColorAsState(
        targetValue = targetColor,
        label = "Health Color"
    )

    val performAction = {
        isAttacking = !isAttacking   // Alternar ataque
        healthLevel = Random.nextInt(0, 101)
        isRight = !isRight
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            "HP: $healthLevel",
            color = animatedHealthColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {

            // Jugador
            Box(
                modifier = Modifier
                    .offset(x = animatedPositionX)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(animatedHealthColor)
                    .border(2.dp, Color.Black, CircleShape)
                    .clickable(onClick = performAction)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    "P",
                    Modifier.align(Alignment.Center),
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            // Ataque
            androidx.compose.animation.AnimatedVisibility(
                visible = isAttacking,
                enter = fadeIn(tween(150)) + expandVertically(),
                exit = fadeOut(tween(400)),
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
                    Text(
                        "¡HIT!",
                        Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Button(onClick = performAction) {
            Text("Mover/Atacar y Recibir Daño")
        }
    }
}

// ----------------------------------------------------
// 🔄 Ejercicio 4
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
            Text("Cambiar Estado (Actual: $currentState)")
        }

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                (slideInVertically(tween(600)) { it } + fadeIn(tween(600)))
                    .togetherWith(slideOutVertically(tween(600)) { -it } + fadeOut(tween(600)))
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
                    Text(
                        "¡Contenido cargado exitosamente!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                }

                ContentState.ERROR -> {
                    Text(
                        "⚠️ Error al obtener datos. Intente de nuevo.",
                        fontSize = 22.sp,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// 📏 Ejercicio 3
// ----------------------------------------------------

@Composable
fun Ejercicio3_AnimateDpAsState(modifier: Modifier = Modifier) {
    var isLarge by remember { mutableStateOf(false) }
    val targetSize = if (isLarge) 250.dp else 100.dp
    val targetOffset = if (isLarge) 100.dp else 0.dp

    val animatedSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = tween(800),
        label = "Size Animation"
    )
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(800),
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
// 🎨 Ejercicio 2
// ----------------------------------------------------

@Composable
fun Ejercicio2_AnimateColorAsState(modifier: Modifier = Modifier) {
    var isBlue by remember { mutableStateOf(true) }
    val targetColor =
        if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50)

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(1000),
        label = "Color Animation"
    )

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { isBlue = !isBlue }, modifier = Modifier.padding(bottom = 32.dp)) {
            Text("Cambiar a ${if (isBlue) "Verde" else "Azul"}")
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(animatedColor)
        )
    }
}

// ----------------------------------------------------
// 🚀 Ejercicio 1
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
            Text(if (isVisible) "Ocultar Cuadro" else "Mostrar Cuadro")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = spring()) +
                    expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut(animationSpec = spring()) +
                    shrinkVertically(shrinkTowards = Alignment.Top)
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
