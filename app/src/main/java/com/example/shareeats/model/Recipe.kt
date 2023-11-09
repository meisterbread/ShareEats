package com.example.shareeats.model

data class Recipe(
    val name: String? = null,
    val ingredients: List<String>? = null,
    val instructions: String? = null,
    val cookingTime: Int? = null,
)
