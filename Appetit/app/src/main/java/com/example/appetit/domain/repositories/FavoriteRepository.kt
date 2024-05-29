package com.example.appetit.domain.repositories

import androidx.lifecycle.LiveData
import com.example.appetit.data.local.FavoriteRecipeDao
import com.example.appetit.data.models.FavoriteRecipe
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteRecipeDao: FavoriteRecipeDao
) {
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
        favoriteRecipeDao.insertFavoriteRecipe(favoriteRecipe)
    }

    suspend fun deleteFavoriteRecipeByUri(uri: String) {
        favoriteRecipeDao.deleteFavoriteRecipeByUri(uri)
    }

    fun isFavorite(uri: String): LiveData<FavoriteRecipe?> {
        return favoriteRecipeDao.getFavoriteByUri(uri)
    }

    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>> {
        return favoriteRecipeDao.getAllFavoriteRecipes()
    }
}

