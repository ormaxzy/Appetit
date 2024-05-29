package com.example.appetit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appetit.data.models.Recipe
import com.example.appetit.domain.usecases.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private var searchJob: Job? = null

    fun searchRecipes(
        query: String,
        mealType: List<String>? = null,
        cuisineType: List<String>? = null,
        dishType: List<String>? = null,
        health: List<String>? = null
    ) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val result = searchRecipesUseCase.execute(query, mealType, cuisineType, dishType, health)
            _recipes.value = result
        }
    }
}
