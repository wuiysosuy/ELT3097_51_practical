package com.example.solvesudokueasy

class SudokuSolver(private val board: Array<IntArray>) {

    fun solve(): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    for (num in 1..9) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num
                            if (solve()) return true
                            board[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isValid(row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until 9) {
            if (board[row][i] == num || board[i][col] == num) return false
        }

        val boxRow = row - row % 3
        val boxCol = col - col % 3

        for (i in boxRow until boxRow + 3) {
            for (j in boxCol until boxCol + 3) {
                if (board[i][j] == num) return false
            }
        }
        return true
    }

    // thêm hàm getter ở đây
    fun getBoard(): Array<IntArray> = board
}
