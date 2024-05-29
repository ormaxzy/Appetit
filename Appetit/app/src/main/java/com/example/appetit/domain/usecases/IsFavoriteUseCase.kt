package com.example.appetit.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.appetit.domain.repositories.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(uri: String): LiveData<Boolean> {
        return favoriteRepository.isFavorite(uri).map { it != null }
    }
}