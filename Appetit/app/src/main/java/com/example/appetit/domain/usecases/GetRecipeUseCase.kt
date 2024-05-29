package com.example.appetit.domain.usecases

import com.example.appetit.data.models.Recipe
import com.example.appetit.domain.repositories.RecipeRepository
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend fun execute(uri: List<String>): List<Recipe> {
        return recipeRepository.getRecipe(uri)
    }
}
