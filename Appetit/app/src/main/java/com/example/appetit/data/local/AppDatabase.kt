package com.example.appetit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appetit.data.models.FavoriteRecipe


@Database(entities = [FavoriteRecipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipeDao(): FavoriteRecipeDao
}
