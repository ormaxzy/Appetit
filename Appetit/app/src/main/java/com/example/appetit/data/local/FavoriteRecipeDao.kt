package com.example.appetit.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appetit.data.models.FavoriteRecipe

@Dao
interface FavoriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe)

    @Query("DELETE FROM FavoriteRecipe WHERE uri = :uri")
    suspend fun deleteFavoriteRecipeByUri(uri: String)

    @Query("SELECT * FROM FavoriteRecipe WHERE uri = :uri LIMIT 1")
    fun getFavoriteByUri(uri: String): LiveData<FavoriteRecipe?>

    @Query("SELECT * FROM FavoriteRecipe")
    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>>
}
