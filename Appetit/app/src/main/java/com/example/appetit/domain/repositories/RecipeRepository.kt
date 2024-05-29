package com.example.appetit.domain.repositories

import com.example.appetit.data.models.Recipe
import com.example.appetit.data.network.EdamamApiService
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val edamamApiService: EdamamApiService
) {

    suspend fun searchRecipes(
        query: String? = null,
        mealType: List<String>? = null,
        cuisineType: List<String>? = null,
        dishType: List<String>? = null,
        health: List<String>? = null
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

    suspend fun getRecipe(uri: List<String>): List<Recipe> {
        return filterRecipes(edamamApiService.getRecipe(uri).hits.map { it.recipe })
    }

    suspend fun getRandomRecipes(): List<Recipe> {
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
