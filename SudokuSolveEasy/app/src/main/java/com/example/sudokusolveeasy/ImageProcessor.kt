package com.example.sudokusolveeasy

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.util.*
import android.content.Context
import kotlin.math.abs

class ImageProcessor {

    fun detectSudokuGrid(image: Mat, context: Context): Array<IntArray> {
        // Convert to grayscale
        val gray = Mat()
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY)

        // Apply Gaussian blur to reduce noise
        Imgproc.GaussianBlur(gray, gray, Size(5.0, 5.0), 0.0)

        // Apply adaptive threshold to create binary image
        val binary = Mat()
        Imgproc.adaptiveThreshold(
            gray, binary, 255.0,
            Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
            Imgproc.THRESH_BINARY_INV, 11, 2.0
        )

        // Find contours
        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()
        Imgproc.findContours(
            binary, contours, hierarchy,
            Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE
        )

        var largestContour: MatOfPoint? = null
        var maxArea = 0.0

        for (contour in contours) {
            val area = Imgproc.contourArea(contour)
            if (area > maxArea) {
                maxArea = area
                largestContour = contour
            }
        }

        if (largestContour != null) {
            val contourFloat = MatOfPoint2f(*largestContour.toArray())
            val corners = MatOfPoint2f()
            Imgproc.approxPolyDP(
                contourFloat, corners,
                0.02 * Imgproc.arcLength(contourFloat, true), true
            )

            if (corners.total() == 4L) {
                val orderedCorners = orderPoints(corners)
                val dst = MatOfPoint2f(
                    Point(0.0, 0.0),
                    Point(450.0, 0.0),
                    Point(450.0, 450.0),
                    Point(0.0, 450.0)
                )

                val perspectiveTransform = Imgproc.getPerspectiveTransform(orderedCorners, dst)

                val warped = Mat()
                Imgproc.warpPerspective(gray, warped, perspectiveTransform, Size(450.0, 450.0))

                val cellSize = warped.width() / 9
                val grid = Array(9) { IntArray(9) }

                val digitRecognizer = DigitRecognizer(context)

                for (i in 0 until 9) {
                    for (j in 0 until 9) {
                        val cell = warped.submat(
                            i * cellSize, (i + 1) * cellSize,
                            j * cellSize, (j + 1) * cellSize
                        )
                        grid[i][j] = digitRecognizer.recognizeDigit(cell)
                    }
                }
                return grid
            }
        }

        return Array(9) { IntArray(9) } // Trả về lưới trống nếu không tìm thấy
    }

    private fun orderPoints(points: MatOfPoint2f): MatOfPoint2f {
        val pts = points.toArray()
        val sorted = pts.sortedWith(compareBy({ it.x + it.y }))

        val topLeft = sorted.first()
        val bottomRight = sorted.last()

        val remaining = pts.filter { it != topLeft && it != bottomRight }
        val (topRight, bottomLeft) = if (remaining[0].x > remaining[1].x) {
            Pair(remaining[0], remaining[1])
        } else {
            Pair(remaining[1], remaining[0])
        }

        return MatOfPoint2f(topLeft, topRight, bottomRight, bottomLeft)
    }
}
