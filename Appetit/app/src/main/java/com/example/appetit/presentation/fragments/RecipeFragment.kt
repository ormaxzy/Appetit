package com.example.appetit.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appetit.R
import com.example.appetit.domain.models.Recipe
import com.example.appetit.databinding.FragmentRecipeBinding
import com.example.appetit.presentation.adapters.IngredientsAdapter
import com.example.appetit.presentation.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupBackButton()
    }

    private fun setupViewModel() {
        viewModel.checkIfFavorite(args.uri)
        viewModel.fetchRecipe(args.uri)
        observeRecipe()
        observeFavoriteState()
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.firstOrNull()
            recipe?.let { updateUI(it) }
        }
    }

    private fun observeFavoriteState() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteButton(isFavorite)
        }
    }

    private fun updateUI(recipe: Recipe) {
        binding.apply {
            labelTextView.text = recipe.label
            cuisineTypeTextView.text = recipe.cuisineType?.joinToString(", ")
            mealTypeTextView.text = recipe.mealType?.joinToString(", ")
            dishTypeTextView.text = recipe.dishType?.joinToString(", ")

            val ingredientLines = recipe.ingredientLines ?: emptyList()
            val ingredientsAdapter = IngredientsAdapter(ingredientLines)
            ingredientsRecyclerView.apply {
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            Glide.with(this@RecipeFragment)
                .load(recipe.image)
                .into(posterImageView)

            setupButton(recipe.url)
            tagsTextView.text = recipe.tags?.joinToString(", ")

            addToFavoriteBtn.setOnClickListener {
                if (viewModel.isFavorite.value == true) {
                    viewModel.removeFromFavorites(recipe.uri)
                } else {
                    viewModel.addToFavorites(recipe)
                }
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val favoriteDrawable = if (isFavorite) {
            R.drawable.star_filled
        } else {
            R.drawable.star
        }
        binding.addToFavoriteBtn.setImageResource(favoriteDrawable)
    }

    private fun setupButton(url: String) {
        binding.openInBrowserButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
