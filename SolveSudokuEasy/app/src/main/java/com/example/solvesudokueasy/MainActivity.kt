package com.example.solvesudokueasy

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var importButton: Button
    private lateinit var solveAllButton: Button
    private lateinit var solveStepButton: Button
    private lateinit var nextButton: Button
    private lateinit var imageView: ImageView
    private var sudokuSolver: SudokuSolver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        importButton = findViewById(R.id.importButton)
        solveAllButton = findViewById(R.id.solveAllButton)
        solveStepButton = findViewById(R.id.solveStepButton)
        nextButton = findViewById(R.id.nextButton)
        imageView = findViewById(R.id.imageView)

        importButton.setOnClickListener { openImagePicker() }
        solveAllButton.setOnClickListener { solveSudokuAll() }
        solveStepButton.setOnClickListener { startStepByStep() }
        nextButton.setOnClickListener { showNextAnswer() }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                imageView.setImageBitmap(bitmap)
                sudokuSolver = ImageProcessor.processImage(bitmap)
            }
        }
    }

    private fun solveSudokuAll() {
        sudokuSolver?.solveAll()?.let { answers ->
            // Hiển thị đáp án toàn bộ
        }
    }

    private fun startStepByStep() {
        // Bắt đầu giải từng bước
    }

    private fun showNextAnswer() {
        // Hiển thị ô đáp án tiếp theo
    }
}