package com.example.appetit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.domain.models.Recipe
import com.example.appetit.domain.usecases.AddToFavoritesUseCase
import com.example.appetit.domain.usecases.GetRecipeUseCase
import com.example.appetit.domain.usecases.IsFavoriteUseCase
import com.example.appetit.domain.usecases.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _recipe = MutableLiveData<List<Recipe>>()
    val recipe: LiveData<List<Recipe>> get() = _recipe

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun fetchRecipe(uri: String) {
        viewModelScope.launch {
            val result = getRecipeUseCase.execute(listOf(uri))
            _recipe.value = result
            checkIfFavorite(uri)
        }
    }

    fun addToFavorites(recipe: Recipe) {
        viewModelScope.launch {
            val favoriteRecipe = FavoriteRecipe(
                uri = recipe.uri,
                label = recipe.label,
                image = recipe.image ?: ""
            )
            addToFavoritesUseCase.execute(favoriteRecipe)
            _isFavorite.value = true
        }
    }

    fun removeFromFavorites(uri: String) {
        viewModelScope.launch {
            removeFromFavoritesUseCase.execute(uri)
            _isFavorite.value = false
        }
    }

    fun checkIfFavorite(uri: String) {
        isFavoriteUseCase.execute(uri).observeForever { isFavorite ->
            _isFavorite.value = isFavorite
        }
    }
}
