package com.example.composition.domain.entity

data class Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val answerRight: Int
        get() = this.sum - this.visibleNumber

}