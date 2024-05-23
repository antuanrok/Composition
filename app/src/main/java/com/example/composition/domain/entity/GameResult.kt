package com.example.composition.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
): Serializable