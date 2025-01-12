package com.example.randomstringgenerator.data.model

data class StringModel (
    val randomText: RandomText
)

data class RandomText (
    val value: String,
    val length: String,
    val created: String
)