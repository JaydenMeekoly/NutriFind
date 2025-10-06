package com.example.nutrifind.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String?,
    val summary: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val extendedIngredients: List<Ingredient>
)

data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Float,
    val unit: String,
    val original: String
)

data class RecipeResponse(
    val results: List<Recipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)
