package com.example.solvesudokueasy

import android.graphics.Bitmap

object DigitRecognizer {

    fun recognize(bitmap: Bitmap): SudokuBoard {
        // Code giả lập nhận dạng. Bạn nên thêm code thật với TFLite hoặc ML Kit.
        val board = Array(9) { IntArray(9) { 0 } }
        // TODO: Implement TensorFlow Lite digit recognition here
        return SudokuBoard(board)
    }
}
