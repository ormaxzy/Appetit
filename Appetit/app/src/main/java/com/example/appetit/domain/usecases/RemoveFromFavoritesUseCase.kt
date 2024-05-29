package com.example.appetit.domain.usecases

import com.example.appetit.domain.repositories.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(uri: String) {
        favoriteRepository.deleteFavoriteRecipeByUri(uri)
    }
}
