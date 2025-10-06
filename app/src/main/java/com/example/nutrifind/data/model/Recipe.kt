package com.example.nutrifind.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val image: String? = null,
    val summary: String = "",
    val readyInMinutes: Int = 0,
    val servings: Int = 0,
    val sourceUrl: String = "",
    val extendedIngredients: List<Ingredient> = emptyList()
)

@Serializable
data class Ingredient(
    val id: Int = 0,
    val name: String = "",
    val amount: Float = 0f,
    val unit: String = "",
    val original: String = ""
)

@Serializable
data class RecipeResponse(
    val results: List<Recipe> = emptyList(),
    val offset: Int = 0,
    val number: Int = 0,
    val totalResults: Int = 0
)
