package com.example.appetit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appetit.data.models.Recipe
import com.example.appetit.domain.usecases.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    private val _randomRecipe = MutableLiveData<Recipe?>()
    val randomRecipe: LiveData<Recipe?> get() = _randomRecipe

    fun getRandomRecipe(mealType: List<String>) {
        viewModelScope.launch {
            val recipes = searchRecipesUseCase.execute(mealType = mealType)
            _randomRecipe.value = recipes.randomOrNull()
        }
    }
}
