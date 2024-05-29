package com.example.appetit.domain.usecases

import com.example.appetit.domain.models.Recipe
import com.example.appetit.domain.repositories.RecipeRepository
import javax.inject.Inject

class GetRandomRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(): List<Recipe> {
        return recipeRepository.getRandomRecipes()
    }
}