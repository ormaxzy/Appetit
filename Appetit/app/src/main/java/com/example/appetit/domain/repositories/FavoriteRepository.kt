package com.example.appetit.domain.repositories

import androidx.lifecycle.LiveData
import com.example.appetit.domain.models.FavoriteRecipe

interface FavoriteRepository {
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe)
    suspend fun deleteFavoriteRecipeByUri(uri: String)
    fun isFavorite(uri: String): LiveData<FavoriteRecipe?>
    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>>
}
