package com.example.sudokusolveeasy

import android.content.Context
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.lang.ClassLoader
import org.opencv.core.Size


class DigitRecognizer(private val context: Context) {
    private var tflite: Interpreter? = null

    init {
        try {
            val modelBuffer = loadModelFile()
            tflite = Interpreter(modelBuffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("digit_classifier.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.length

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun recognizeDigit(cell: Mat): Int {
        // Resize to 28x28
        val resized = Mat()
        Imgproc.resize(cell, resized, Size(28.0, 28.0))

        // Convert to grayscale if not already
        val gray = Mat()
        Imgproc.cvtColor(resized, gray, Imgproc.COLOR_BGR2GRAY)

        // Normalize to [0,1]
        val floatMat = Mat()
        gray.convertTo(floatMat, CvType.CV_32FC1, 1.0 / 255.0)

        // Convert Mat to float array
        val inputArray = Array(1) { Array(28) { FloatArray(28) } }
        for (i in 0 until 28) {
            for (j in 0 until 28) {
                inputArray[0][i][j] = floatMat.get(i, j)[0].toFloat()
            }
        }

        // Output array: 10 classes (digits 0-9)
        val outputArray = Array(1) { FloatArray(10) }

        // Run inference
        tflite?.run(inputArray, outputArray)

        // Get max index = predicted digit
        val probs = outputArray[0]
        val predictedDigit = probs.indices.maxByOrNull { probs[it] } ?: 0

        return predictedDigit
    }
}