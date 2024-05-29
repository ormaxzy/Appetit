package com.example.appetit.domain.usecases

import androidx.lifecycle.LiveData
import com.example.appetit.data.models.FavoriteRecipe
import com.example.appetit.domain.repositories.FavoriteRepository
import javax.inject.Inject

class GetFavoriteRecipesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(): LiveData<List<FavoriteRecipe>> {
        return favoriteRepository.getAllFavoriteRecipes()
    }
}
