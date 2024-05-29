package com.example.appetit.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.domain.models.Recipe
import com.example.appetit.domain.usecases.GetFavoriteRecipesUseCase
import com.example.appetit.domain.usecases.GetRandomRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val KEY_THEME = "is_dark_theme"
    }

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val favoriteRecipes: LiveData<List<FavoriteRecipe>> = getFavoriteRecipesUseCase.execute()

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    init {
        fetchRandomRecipes()
        applySavedTheme()
    }

    private fun fetchRandomRecipes() {
        viewModelScope.launch {
            try {
                val randomRecipes = getRandomRecipeUseCase.execute()
                _recipes.value = randomRecipes
            } catch (e: Exception) {
                _recipes.value = emptyList()
            }
        }
    }

    fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(KEY_THEME, false)
    }

    fun setTheme(isDark: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_THEME, isDark).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun applySavedTheme() {
        val isDark = isDarkTheme()
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
