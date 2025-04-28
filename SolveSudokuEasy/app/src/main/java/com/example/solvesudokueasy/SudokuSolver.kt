package com.example.solvesudokueasy

class SudokuSolver(private val board: Array<IntArray>) {
    fun solveAll(): Array<IntArray> {
        if (solve(0, 0)) {
            return board
        }
        throw IllegalArgumentException("Không thể giải câu đố Sudoku này.")
    }

    private fun solve(row: Int, col: Int): Boolean {
        // Nếu đã đến cuối bảng, trả về true
        if (row == 9) return true

        // Nếu cột đã đến cuối, chuyển sang hàng tiếp theo
        if (col == 9) return solve(row + 1, 0)

        // Nếu ô đã có giá trị, chuyển sang ô tiếp theo
        if (board[row][col] != 0) return solve(row, col + 1)

        // Thử các giá trị từ 1 đến 9
        for (num in 1..9) {
            if (isSafe(row, col, num)) {
                board[row][col] = num

                // Đệ quy để giải ô tiếp theo
                if (solve(row, col + 1)) {
                    return true
                }

                // Nếu không thể giải, đặt lại ô
                board[row][col] = 0
            }
        }
        return false
    }

    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        // Kiểm tra hàng
        for (x in 0 until 9) {
            if (board[row][x] == num) return false
        }

        // Kiểm tra cột
        for (x in 0 until 9) {
            if (board[x][col] == num) return false
        }

        // Kiểm tra ô 3x3
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i + startRow