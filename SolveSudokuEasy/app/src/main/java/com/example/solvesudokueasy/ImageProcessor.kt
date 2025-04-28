package com.example.solvesudokueasy

import android.graphics.Bitmap
import android.graphics.Color
import org.opencv.core.*
import org.opencv.android.Utils
import org.opencv.imgproc.Imgproc

object ImageProcessor {
    init {
        System.loadLibrary("opencv_java4") // Tải thư viện OpenCV
    }

    fun processImage(bitmap: Bitmap): SudokuSolver {
        // Chuyển đổi Bitmap thành Mat
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        // Chuyển đổi sang ảnh xám
        val gray = Mat()
        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY)

        // Làm mờ ảnh
        val blurred = Mat()
        Imgproc.GaussianBlur(gray, blurred, Size(5.0, 5.0), 0.0)

        // Phát hiện cạnh
        val edges = Mat()
        Imgproc.Canny(blurred, edges, 50.0, 150.0)

        // Tìm các đường viền
        val contours = mutableListOf<MatOfPoint>()
        val hierarchy = Mat()
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        // Tìm khung Sudoku
        var sudokuContour: MatOfPoint? = null
        for (contour in contours) {
            val perimeter = Imgproc.arcLength(MatOfPoint2f(contour.toArray()), true)
            val approx = MatOfPoint2f()
            Imgproc.approxPolyDP(MatOfPoint2f(contour.toArray()), approx, 0.02 * perimeter, true)

            // Kiểm tra xem có phải là hình chữ nhật không
            if (approx.total() == 4L) {
                sudokuContour = contour
                break
            }
        }

        // Nếu tìm thấy khung Sudoku, chuyển đổi nó thành bảng Sudoku
        val board = Array(9) { IntArray(9) }
        sudokuContour?.let { contour ->
            // Giả sử bạn đã có logic để điền vào bảng từ hình ảnh
            // Duyệt qua các ô trong khung Sudoku
            for (i in 0 until 9) {
                for (j in 0 until 9) {
                    // Giả định rằng bạn có một hàm để lấy giá trị từ contour
                    val value = getValueFromContour(contour, i, j)
                    if (value != 0) { // Nếu giá trị không phải là 0 (ô trống)
                        board[i][j] = value
                    }
                }
            }
        }


        return SudokuSolver(board)
    }
}