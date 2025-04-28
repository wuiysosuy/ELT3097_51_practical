package com.example.sudokusolveeasy

class SudokuSolver {

    fun solve(board: Array<IntArray>): Array<IntArray> {
        // Make a copy of the board to avoid modifying the original
        val solution = Array(9) { i -> IntArray(9) { j -> board[i][j] } }

        solveHelper(solution)
        return solution
    }

    private fun solveHelper(board: Array<IntArray>): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                // Skip filled cells
                if (board[row][col] != 0) {
                    continue
                }

                // Try digits 1-9
                for (num in 1..9) {
                    if (isValid(board, row, col, num)) {
                        // Place the digit
                        board[row][col] = num

                        // Recursively solve the rest
                        if (solveHelper(board)) {
                            return true
                        }

                        // If we get here, we need to backtrack
                        board[row][col] = 0
                    }
                }

                // No valid digit found
                return false
            }
        }

        // All cells filled
        return true
    }

    private fun isValid(board: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        // Check row
        for (i in 0 until 9) {
            if (board[row][i] == num) {
                return false
            }
        }

        // Check column
        for (i in 0 until 9) {
            if (board[i][col] == num) {
                return false
            }
        }

        // Check 3x3 box
        val boxRow = row - row % 3
        val boxCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[boxRow + i][boxCol + j] == num) {
                    return false
                }
            }
        }

        return true
    }

    // Method to get the next empty cell for step-by-step solving
    fun getNextEmptyCell(board: Array<IntArray>): IntArray? {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    return intArrayOf(row, col)
                }
            }
        }
        return null // No empty cells
    }

    // Method to get the next value for a specific cell
    fun getNextValue(board: Array<IntArray>, row: Int, col: Int): Int {
        for (num in 1..9) {
            if (isValid(board, row, col, num)) {
                return num
            }
        }
        return -1 // No valid value
    }
}
