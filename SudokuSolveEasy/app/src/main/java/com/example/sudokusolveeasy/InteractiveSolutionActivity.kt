package com.example.sudokusolveeasy

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import missing.namespace.R

class InteractiveSolutionActivity : AppCompatActivity() {
    private lateinit var originalGrid: IntArray
    private lateinit var solvedGrid: IntArray
    private val currentGrid = Array(9) { IntArray(9) }
    private val isOriginal = Array(9) { BooleanArray(9) }

    private lateinit var sudokuGridView: SudokuGridView
    private lateinit var nextButton: Button
    private lateinit var solveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interactive)

        // Get puzzle data from intent
        originalGrid = intent.getIntArrayExtra("originalGrid") ?: IntArray(81)
        solvedGrid = intent.getIntArrayExtra("solvedGrid") ?: IntArray(81)

        // Initialize UI components
        sudokuGridView = findViewById(R.id.sudokuGridView)
        nextButton = findViewById(R.id.nextButton)
        solveButton = findViewById(R.id.solveButton)

        // Initialize current grid with original values
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                currentGrid[i][j] = originalGrid[i * 9 + j]
                isOriginal[i][j] = originalGrid[i * 9 + j] != 0
            }
        }

        // Set the grid on the custom view
        sudokuGridView.setGrid(currentGrid, isOriginal)

        // Set button listeners
        nextButton.setOnClickListener {
            fillNextEmptyCell()
            sudokuGridView.setGrid(currentGrid, isOriginal)
        }

        solveButton.setOnClickListener {
            for (i in 0 until 9) {
                for (j in 0 until 9) {
                    currentGrid[i][j] = solvedGrid[i * 9 + j]
                }
            }
            sudokuGridView.setGrid(currentGrid, isOriginal)
            nextButton.isEnabled = false
        }
    }

    private fun fillNextEmptyCell() {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (currentGrid[i][j] == 0) {
                    currentGrid[i][j] = solvedGrid[i * 9 + j]
                    return
                }
            }
        }
    }
}