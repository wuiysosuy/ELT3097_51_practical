package com.example.sudokusolveeasy

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.sudokusolveeasy.ProcessingActivity


class MainActivity : AppCompatActivity() {
    private lateinit var importButton: Button

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { processSudokuImage(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        importButton = findViewById(R.id.importButton)
        importButton.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
    }

    private fun processSudokuImage(imageUri: Uri) {
        val intent = Intent(this, ProcessingActivity::class.java)
        intent.putExtra("imageUri", imageUri.toString())
        startActivity(intent)
    }
}
