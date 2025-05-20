package com.example.congcungbe.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class GameViewModel(totalInit: Int, gridSize: Int) : ViewModel() {
    private val _total = MutableStateFlow(totalInit)
    val total = _total.asStateFlow()

    private val _numbers = MutableStateFlow(generateValidNumbers(totalInit, gridSize))
    val numbers = _numbers.asStateFlow()

    private val _selected = MutableStateFlow<List<Int>>(emptyList())
    val selected = _selected.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    fun selectNumber(number: Int) {
        if (_selected.value.contains(number)) return
        val newSelection = _selected.value + number
        _selected.value = newSelection

        if (newSelection.size == 2) {
            if (newSelection.sum() == _total.value) {
                _message.value = "Đúng rồi!"
                _score.value += 1
                _numbers.value = _numbers.value.filter { it !in newSelection }
            } else {
                _message.value = "Sai rồi! Thử lại nhé."
            }
            _selected.value = emptyList()
        }
    }

    fun resetGame() {
        _numbers.value = generateValidNumbers(_total.value, _numbers.value.size)
        _message.value = ""
        _selected.value = emptyList()
    }

    private fun generateValidNumbers(total: Int, size: Int): List<Int> {
        val list = mutableSetOf<Pair<Int, Int>>()

        // Tạo cặp số hợp lệ (x + y = total)
        while (list.size < size / 2) {
            val x = Random.nextInt(1, total)
            val y = total - x
            if (x in 1..99 && y in 1..99 && x != y) {
                list.add(if (x < y) Pair(x, y) else Pair(y, x))
            }
        }

        // Trộn cặp và cắt thành dãy số
        return list.flatMap { listOf(it.first, it.second) }
            .shuffled()
            .take(size)
    }
}
