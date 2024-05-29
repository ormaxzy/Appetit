package com.example.appetit.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteRecipe")
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val uri: String,
    val image: String
)
