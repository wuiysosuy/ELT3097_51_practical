package com.example.sudokusolveeasy

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins

class SolutionActivity : AppCompatActivity() {
    private lateinit var originalGrid: IntArray
    private lateinit var solvedGrid: IntArray
    private val currentGrid = Array(9) { IntArray(9) }

    private lateinit var sudokuGridLayout: GridLayout
    private lateinit var completeButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)

        // Get puzzle data from intent
        originalGrid = intent.getIntArrayExtra("originalGrid") ?: IntArray(81)
        solvedGrid = intent.getIntArrayExtra("solvedGrid") ?: IntArray(81)

        // Initialize UI components
        sudokuGridLayout = findViewById(R.id.sudokuGridLayout)
        completeButton = findViewById(R.id.completeButton)
        nextButton = findViewById(R.id.nextButton)

        // Initialize current grid with original values
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                currentGrid[i][j] = originalGrid[i * 9 + j]
            }
        }

        // Display the initial grid
        displayGrid()

        // Set up button listeners
        completeButton.setOnClickListener {
            // Display full solution
            for (i in 0 until 9) {
                for (j in 0 until 9) {
                    currentGrid[i][j] = solvedGrid[i * 9 + j]
                }
            }
            displayGrid()
            nextButton.isEnabled = false
        }

        nextButton.setOnClickListener {
            // Show next step
            fillNextEmptyCell()
            displayGrid()
        }
    }

    private fun displayGrid() {
        sudokuGridLayout.removeAllViews()

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                val cell = TextView(this)
                val value = currentGrid[i][j]

                // Set text value
                cell.text = if (value == 0) "" else value.toString()

                // Set appearance
                cell.textSize = 24f
                cell.gravity = Gravity.CENTER

                // Add borders
                val boxRow = i / 3
                val boxCol = j / 3
                val backgroundColor = if ((boxRow + boxCol) % 2 == 0)
                    0xFFEEEEEE.toInt() else 0xFFDDDDDD.toInt()
                cell.setBackgroundColor(backgroundColor)

                // Bold original values
                if (originalGrid[i * 9 + j] != 0) {
                    cell.setTypeface(null, android.graphics.Typeface.BOLD)
                }

                // Set layout parameters
                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = 0
                params.columnSpec = GridLayout.spec(j, 1, 1f)
                params.rowSpec = GridLayout.spec(i, 1, 1f)
                cell.layoutParams = params

                sudokuGridLayout.addView(cell)
            }
        }
    }

    private fun fillNextEmptyCell() {
        // Find next empty cell
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (currentGrid[i][j] == 0) {
                    // Fill with solution value
                    currentGrid[i][j] = solvedGrid[i * 9 + j]
                    return
                }
            }
        }
    }
}
