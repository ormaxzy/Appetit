package com.example.appetit.presentation.adapters

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appetit.R
import com.example.appetit.domain.models.FavoriteRecipe
import com.example.appetit.databinding.ItemFavoriteStoryBinding
import kotlin.math.min

class FavoriteStoryAdapter(
    private val onRecipeClick: (FavoriteRecipe) -> Unit,
    private val onSeeAllClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Any> = listOf()

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size - 1) VIEW_TYPE_BUTTON else VIEW_TYPE_RECIPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RECIPE) {
            RecipeViewHolder(
                ItemFavoriteStoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_see_all_button, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> holder.bind(items[position] as FavoriteRecipe)
            is ButtonViewHolder -> holder.bind(onSeeAllClick)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(recipes: List<FavoriteRecipe>) {
        items = recipes.take(9) + Any()
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val binding: ItemFavoriteStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: FavoriteRecipe) {
            binding.favoriteText.text = recipe.label
            setupImageView(binding.favoriteImage, recipe.image)
            binding.root.setOnClickListener { onRecipeClick(recipe) }
        }

        private fun setupImageView(view: View, imageUrl: String) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val diameter = min(view.width, view.height)
                    outline.setOval(0, 0, diameter, diameter)
                }
            }
            view.clipToOutline = true
            Glide.with(view.context)
                .load(imageUrl)
                .into(binding.favoriteImage)
        }
    }

    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(onSeeAllClick: () -> Unit) {
            itemView.setOnClickListener { onSeeAllClick() }
        }
    }

    companion object {
        private const val VIEW_TYPE_RECIPE = 0
        private const val VIEW_TYPE_BUTTON = 1
    }
}
