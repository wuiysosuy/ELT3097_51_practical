package com.example.solvesudokueasy

class SudokuBoard(var board: Array<IntArray>) {

    private val solver = SudokuSolver(board)

    fun solveSudoku() {
        solver.solve()
    }

    fun fillNextCell() {
        solver.solve()
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    board[row][col] = solver.getBoard()[row][col]
                    return
                }
            }
        }
    }
}