package dev.kimchiloof.financially.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun HalfCircleTracker(spendingGoal: Double, amountSpent: Double) {
    val spentPercentage = (amountSpent / spendingGoal).toFloat().coerceIn(0f, 1f)
    val remainingPercentage = 1 - spentPercentage

    Canvas(modifier = Modifier.size(200.dp)) {
        // Draw the spent portion (red arc)
        drawArc(
            color = Color.Red,
            startAngle = 180f,
            sweepAngle = 180f * spentPercentage,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        // Draw the remaining portion (green arc)
        drawArc(
            color = Color.Green,
            startAngle = 180f + 180f * spentPercentage,
            sweepAngle = 180f * remainingPercentage,
            useCenter = false,
            style = Stroke(width = 40f)
        )
    }
}