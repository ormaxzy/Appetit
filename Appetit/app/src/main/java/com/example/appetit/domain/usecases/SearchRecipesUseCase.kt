package com.example.appetit.domain.usecases

import com.example.appetit.data.models.Recipe
import com.example.appetit.domain.repositories.RecipeRepository
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(
        query: String? = null,
        mealType: List<String>? = null,
        cuisineType: List<String>? = null,
        dishType: List<String>? = null,
        health: List<String>? = null
    ): List<Recipe> {
        return recipeRepository.searchRecipes(query, mealType, cuisineType, dishType, health)
    }
}
