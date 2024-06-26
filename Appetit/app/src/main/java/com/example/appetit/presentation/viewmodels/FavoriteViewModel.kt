package com.example.appetit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.domain.usecases.GetFavoriteRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
) : ViewModel() {

    val favoriteRecipes: LiveData<List<FavoriteRecipe>> = getFavoriteRecipesUseCase.execute()
}
