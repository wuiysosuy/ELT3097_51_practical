package com.example.sudokusolveeasy

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat

class ProcessingActivity : AppCompatActivity() {
    private val sudokuGrid = Array(9) { IntArray(9) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // Initialize OpenCV
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV initialization failed", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Get image URI from intent
        val imageUriString = intent.getStringExtra("imageUri")
        if (imageUriString == null) {
            Toast.makeText(this, "Image URI not found", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val imageUri = Uri.parse(imageUriString)
        processSudokuImage(imageUri)
    }

    private fun processSudokuImage(imageUri: Uri) {
        try {
            // Load image
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

            // Convert to OpenCV format
            val imageMat = Mat()
            Utils.bitmapToMat(bitmap, imageMat)

            // Process image to detect Sudoku grid
            val detectedGrid = detectSudokuGrid(imageMat)

            // Solve the Sudoku puzzle
            val solver = SudokuSolver()
            val solution = solver.solve(detectedGrid)

            // Navigate to solution activity
            val intent = Intent(this, SolutionActivity::class.java)
            intent.putExtra("originalGrid", flatten(detectedGrid))
            intent.putExtra("solvedGrid", flatten(solution))
            startActivity(intent)
            finish()

        } catch (e: Exception) {
            Toast.makeText(this, "Error processing image: ${e.message}",
                Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun detectSudokuGrid(image: Mat): Array<IntArray> {
        // TODO: Implement Sudoku grid detection using OpenCV
        // This will include:
        // 1. Find the Sudoku grid in the image
        // 2. Extract individual cells
        // 3. Recognize digits using TensorFlow Lite model

        // Placeholder implementation that returns an empty grid
        return Array(9) { IntArray(9) }
    }

    private fun flatten(grid: Array<IntArray>): IntArray {
        val flat = IntArray(81)
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                flat[i * 9 + j] = grid[i][j]
            }
        }
        return flat
    }
}
