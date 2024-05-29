package com.example.appetit.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appetit.R
import com.example.appetit.domain.models.Recipe
import com.example.appetit.databinding.ItemRecipeBinding

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onItemClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            val context = binding.root.context
            val unknownText = context.getString(R.string.unknown)
            val kcalFormat = context.getString(R.string.kcal_format)

            binding.apply {
                labelTextView.text = recipe.label
                cuisineTypeTextView.text = recipe.cuisineType?.joinToString(", ") ?: unknownText
                mealTypeTextView.text = recipe.mealType?.joinToString(", ") ?: unknownText
                dishTypeTextView.text = recipe.dishType?.joinToString(", ") ?: unknownText
                tagsTextView.text = recipe.tags?.joinToString(", ") ?: unknownText
                caloriesTextView.text = String.format(kcalFormat, recipe.calories.toInt())

                Glide.with(posterImageView.context)
                    .load(recipe.image)
                    .into(posterImageView)

                root.setOnClickListener {
                    onItemClick(recipe)
                }
            }
        }
    }
}
