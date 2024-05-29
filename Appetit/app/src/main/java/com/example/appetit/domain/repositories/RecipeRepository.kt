package com.example.appetit.domain.repositories

import com.example.appetit.domain.models.Recipe

interface RecipeRepository {
    suspend fun searchRecipes(
        query: String? = null,
        mealType: List<String>? = null,
        cuisineType: List<String>? = null,
        dishType: List<String>? = null,
        health: List<String>? = null
    ): List<Recipe>

    suspend fun getRecipe(uri: List<String>): List<Recipe>

    suspend fun getRandomRecipes(): List<Recipe>
}
