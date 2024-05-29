
package com.example.appetit.data.repositories

import androidx.lifecycle.LiveData
import com.example.appetit.data.local.FavoriteRecipeDao
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.domain.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteRecipeDao: FavoriteRecipeDao
) : FavoriteRepository {
    override suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
        favoriteRecipeDao.insertFavoriteRecipe(favoriteRecipe)
    }

    override suspend fun deleteFavoriteRecipeByUri(uri: String) {
        favoriteRecipeDao.deleteFavoriteRecipeByUri(uri)
    }

    override fun isFavorite(uri: String): LiveData<FavoriteRecipe?> {
        return favoriteRecipeDao.getFavoriteByUri(uri)
    }

    override fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>> {
        return favoriteRecipeDao.getAllFavoriteRecipes()
    }
}
