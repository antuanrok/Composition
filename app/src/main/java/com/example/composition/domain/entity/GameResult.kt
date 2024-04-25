package com.example.composition.domain.entity

data class GameResult(
    val winner: Boolean,
    val countRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)