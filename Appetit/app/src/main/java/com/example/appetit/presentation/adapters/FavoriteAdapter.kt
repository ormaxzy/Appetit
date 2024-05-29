package com.example.appetit.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.databinding.ItemFavoriteBinding

class FavoriteAdapter(private val onClick: (FavoriteRecipe) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteRecipes = listOf<FavoriteRecipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteRecipes[position])
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    fun submitList(recipes: List<FavoriteRecipe>) {
        favoriteRecipes = recipes
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: FavoriteRecipe) {
            with(binding) {
                recipeTitle.text = recipe.label
                Glide.with(recipeImage.context)
                    .load(recipe.image)
                    .into(recipeImage)

                root.setOnClickListener {
                    onClick(recipe)
                }
            }
        }
    }
}
