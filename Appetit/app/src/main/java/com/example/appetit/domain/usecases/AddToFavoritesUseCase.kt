package com.example.appetit.domain.usecases

import com.example.appetit.data.models.FavoriteRecipe
import com.example.appetit.domain.repositories.FavoriteRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(favoriteRecipe: FavoriteRecipe) {
        favoriteRepository.insertFavoriteRecipe(favoriteRecipe)
    }
}