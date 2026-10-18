package com.ciro.vr.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.PeachDark
import com.ciro.vr.ui.theme.PeachPrimary
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun DiceView(
    number: Int,
    isRolling: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp
) {
    var displayValue by remember { mutableIntStateOf(number) }

    LaunchedEffect(isRolling, number) {
        if (isRolling) {
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTime < 800) {
                displayValue = Random.nextInt(1, 7)
                delay(60)
            }
            displayValue = number
        } else {
            displayValue = number
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "dice_roll")
    val rollRotation by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(80, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    val rollScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val currentRotation = if (isRolling) rollRotation else 0f
    val currentScale = if (isRolling) rollScale else 1f

    Box(
        modifier = modifier
            .size(size)
            .scale(currentScale)
            .rotate(currentRotation)
            .shadow(16.dp, shape = RoundedCornerShape(28.dp), spotColor = PeachPrimary.copy(alpha = 0.4f))
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF2E2723),
                        Color(0xFF1B1715)
                    )
                )
            )
            .border(2.5.dp, Brush.linearGradient(listOf(PeachPrimary, PeachDark, DarkBorder)), RoundedCornerShape(28.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        DiceDotsCanvas(
            number = displayValue,
            dotColor = PeachPrimary,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )
    }
}

@Composable
fun DiceDotsCanvas(
    number: Int,
    dotColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val r = w * 0.10f

        val left = w * 0.25f
        val right = w * 0.75f
        val center = w * 0.50f

        val top = h * 0.25f
        val bottom = h * 0.75f
        val middle = h * 0.50f

        fun drawDot(x: Float, y: Float) {
            // Shadow behind dot
            drawCircle(
                color = DarkBackground.copy(alpha = 0.7f),
                radius = r * 1.05f,
                center = Offset(x, y + 2f)
            )
            // Main dot
            drawCircle(
                color = dotColor,
                radius = r,
                center = Offset(x, y)
            )
            // Subtle highlight reflection
            drawCircle(
                color = Color.White.copy(alpha = 0.45f),
                radius = r * 0.35f,
                center = Offset(x - r * 0.3f, y - r * 0.3f)
            )
        }

        when (number.coerceIn(1, 6)) {
            1 -> {
                drawDot(center, middle)
            }
            2 -> {
                drawDot(left, top)
                drawDot(right, bottom)
            }
            3 -> {
                drawDot(left, top)
                drawDot(center, middle)
                drawDot(right, bottom)
            }
            4 -> {
                drawDot(left, top)
                drawDot(right, top)
                drawDot(left, bottom)
                drawDot(right, bottom)
            }
            5 -> {
                drawDot(left, top)
                drawDot(right, top)
                drawDot(center, middle)
                drawDot(left, bottom)
                drawDot(right, bottom)
            }
            6 -> {
                drawDot(left, top)
                drawDot(right, top)
                drawDot(left, middle)
                drawDot(right, middle)
                drawDot(left, bottom)
                drawDot(right, bottom)
            }
        }
    }
}
