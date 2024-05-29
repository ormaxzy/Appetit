package com.example.appetit.data.repositories

import com.example.appetit.domain.models.Recipe
import com.example.appetit.data.network.EdamamApiService
import com.example.appetit.domain.repositories.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val edamamApiService: EdamamApiService
) : RecipeRepository {

    override suspend fun searchRecipes(
        query: String?,
        mealType: List<String>?,
        cuisineType: List<String>?,
        dishType: List<String>?,
        health: List<String>?
    ): List<Recipe> {
        val allFilteredRecipes = mutableListOf<Recipe>()
        var nextPageUrl: String? = null

        do {
            val response = fetchRecipes(nextPageUrl, query, mealType, cuisineType, dishType, health)
            val filteredRecipes = filterRecipes(response.hits.map { it.recipe })
            allFilteredRecipes.addAll(filteredRecipes)
            nextPageUrl = response.links.next?.href
        } while (nextPageUrl != null && allFilteredRecipes.size < 50)

        return allFilteredRecipes
    }

    override suspend fun getRecipe(uri: List<String>): List<Recipe> {
        return filterRecipes(edamamApiService.getRecipe(uri).hits.map { it.recipe })
    }

    override suspend fun getRandomRecipes(): List<Recipe> {
        val response = edamamApiService.getRandomRecipes()
        return filterRecipes(response.hits.map { it.recipe })
    }

    private suspend fun fetchRecipes(
        nextPageUrl: String?,
        query: String?,
        mealType: List<String>?,
        cuisineType: List<String>?,
        dishType: List<String>?,
        health: List<String>?
    ) = nextPageUrl?.let { edamamApiService.getNextPage(it) }
        ?: edamamApiService.searchRecipes(query, mealType, cuisineType, dishType, health)

    private fun filterRecipes(recipes: List<Recipe>): List<Recipe> {
        return recipes.filter { recipe ->
            !recipe.cuisineType.isNullOrEmpty() &&
                    !recipe.mealType.isNullOrEmpty() &&
                    !recipe.dishType.isNullOrEmpty() &&
                    !recipe.ingredientLines.isNullOrEmpty() &&
                    !recipe.image.isNullOrEmpty() &&
                    !recipe.tags.isNullOrEmpty()
        }
    }
}
