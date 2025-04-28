package com.example.sudokusolveeasy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class SudokuGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val grid = Array(9) { IntArray(9) }
    private val originalCells = Array(9) { BooleanArray(9) }

    private val cellPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2f
    }

    private val boldLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 5f
    }

    private val numberPaint = Paint().apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 40f
    }

    private val originalNumberPaint = Paint().apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 40f
        isFakeBoldText = true
    }

    fun setGrid(newGrid: Array<IntArray>, isOriginal: Array<BooleanArray>) {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                grid[i][j] = newGrid[i][j]
                originalCells[i][j] = isOriginal[i][j]
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val cellWidth = width / 9
        val cellHeight = height / 9

        // Draw cells
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                // Draw cell background
                canvas.drawRect(
                    j * cellWidth,
                    i * cellHeight,
                    (j + 1) * cellWidth,
                    (i + 1) * cellHeight,
                    cellPaint
                )

                // Draw number if present
                if (grid[i][j] != 0) {
                    val text = grid[i][j].toString()
                    val paint = if (originalCells[i][j]) originalNumberPaint else numberPaint
                    canvas.drawText(
                        text,
                        (j + 0.5f) * cellWidth,
                        (i + 0.5f) * cellHeight + numberPaint.textSize / 3,
                        paint
                    )
                }
            }
        }

        // Draw grid lines
        for (i in 0 until 10) {
            val paint = if (i % 3 == 0) boldLinePaint else linePaint

            // Horizontal lines
            canvas.drawLine(
                0f,
                i * cellHeight,
                width,
                i * cellHeight,
                paint
            )

            // Vertical lines
            canvas.drawLine(
                i * cellWidth,
                0f,
                i * cellWidth,
                height,
                paint
            )
        }
    }
}