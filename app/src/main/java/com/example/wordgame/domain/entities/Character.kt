package com.example.wordgame.domain.entities

data class Character(
    var value: String,
    var isFreeze: Boolean = false,
    var isActive: Boolean = true
)