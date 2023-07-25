package com.example.placeholder

// HalfCircleTrackerView.kt
import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class HalfCircleTrackerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Custom properties
    private var progress = 0.5f // Initial progress (from 0.0 to 1.0)
    private val startAngle = -90f // Start angle of the half-circle (12 o'clock position)
    private val sweepAngle = 180f // Sweep angle of the half-circle (180 degrees)

    private val backgroundColor = Color.RED
    private val progressColor = Color.GREEN
    private val strokeWidth = 10f // Width of the tracker stroke

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = backgroundColor
        strokeWidth = this@HalfCircleTrackerView.strokeWidth
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = progressColor
        strokeWidth = this@HalfCircleTrackerView.strokeWidth
    }
    private val progressRectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Set the arc bounds as a square inside the view
        val arcRadius = (width - strokeWidth) / 2f
        progressRectF.set(centerX - arcRadius, centerY - arcRadius, centerX + arcRadius, centerY + arcRadius)

        // Draw the background arc
        canvas.drawArc(progressRectF, startAngle, sweepAngle, false, backgroundPaint)

        // Draw the progress arc
        canvas.drawArc(progressRectF, startAngle, sweepAngle * progress, false, progressPaint)
    }

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 1f) // Clamp progress value between 0 and 1

        // Update the progress color based on the progress value (from red to green)
        val interpolatedColor = ArgbEvaluator().evaluate(progress, backgroundColor, progressColor) as Int
        progressPaint.color = interpolatedColor

        invalidate() // Redraw the view with the updated progress
    }

}

