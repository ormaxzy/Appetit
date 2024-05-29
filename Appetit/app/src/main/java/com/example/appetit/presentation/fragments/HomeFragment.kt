package com.example.appetit.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appetit.R
import com.example.appetit.databinding.FragmentHomeBinding
import com.example.appetit.presentation.adapters.FavoriteStoryAdapter
import com.example.appetit.presentation.adapters.RecipeAdapter
import com.example.appetit.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var favoriteStoryAdapter: FavoriteStoryAdapter
    private val recipeAdapter by lazy {
        RecipeAdapter(emptyList()) { recipe ->
            val action = HomeFragmentDirections.actionNavigationHomeToRecipeFragment(recipe.uri)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupThemeSwitch()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        favoriteStoryAdapter = FavoriteStoryAdapter(
            onRecipeClick = { recipe ->
                val action = HomeFragmentDirections.actionNavigationHomeToRecipeFragment(recipe.uri)
                findNavController().navigate(action)
            },
            onSeeAllClick = {
                findNavController().navigate(R.id.action_navigation_home_to_favoriteFragment)
            }
        )
        binding.favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteStoryAdapter
        }
        binding.randomRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    private fun setupThemeSwitch() {
        binding.themeSwitch.isChecked = homeViewModel.isDarkTheme()
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.setTheme(isChecked)
        }
    }

    private fun observeViewModel() {
        homeViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateRecipes(recipes)
            binding.noResultsLayout.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
        }
        homeViewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            if (recipes.isNullOrEmpty()) {
                binding.willBeTextView.visibility = View.VISIBLE
                binding.favoriteRecyclerView.visibility = View.GONE
            } else {
                binding.willBeTextView.visibility = View.GONE
                binding.favoriteRecyclerView.visibility = View.VISIBLE
                favoriteStoryAdapter.submitList(recipes)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoriteRecyclerView.adapter = null
        binding.randomRecyclerView.adapter = null
        _binding = null
    }
}
