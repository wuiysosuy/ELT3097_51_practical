package com.example.congcungbe.models

data class GameState(
    val total: Int = 10,
    val numbers: List<Int> = (1..20).shuffled().take(12),
    val selected: List<Int> = emptyList(),
    val message: String = "",
    val score: Int = 0
)
