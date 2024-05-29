package com.example.appetit.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appetit.databinding.FragmentSearchBinding
import com.example.appetit.presentation.adapters.RecipeAdapter
import com.example.appetit.presentation.viewmodels.SearchViewModel
import com.example.appetit.presentation.viewmodels.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.appetit.R
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private val filtersViewModel: FiltersViewModel by activityViewModels()
    private val recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
        val action = SearchFragmentDirections.actionNavigationSearchToRecipeFragment(recipe.uri)
        findNavController().navigate(action)
    }
    private val loadingTextHandler = Handler(Looper.getMainLooper())
    private var loadingTextRunnable: Runnable? = null
    private lateinit var loadingTexts: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        loadingTexts = resources.getStringArray(R.array.loading_texts)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeRecipes()
        setupSearchView()
        setupFilterButton()
        observeFilters()
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeRecipes() {
        searchViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateRecipes(recipes)
            binding.loadingAnimationView.visibility = View.GONE
            binding.loadingTextView.visibility = View.GONE
            binding.searchView.isEnabled = true
            binding.filterButton.isEnabled = true
            binding.noResultsLayout.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    performSearch(query)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupFilterButton() {
        binding.filterButton.setOnClickListener {
            val action = SearchFragmentDirections.actionNavigationSearchToFiltersFragment()
            findNavController().navigate(
                action, NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_right)
                    .setPopEnterAnim(R.anim.slide_in_right)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build()
            )
        }
    }

    private fun observeFilters() {
        filtersViewModel.filters.observe(viewLifecycleOwner) { _ ->
            val query = binding.searchView.query.toString()
            if (query.isNotEmpty() && query.length > 3) {
                performSearch(query)
            }
        }
    }

    private fun performSearch(query: String) {
        val filters = filtersViewModel.filters.value
        searchViewModel.searchRecipes(
            query,
            mealType = filters?.get("mealType"),
            cuisineType = filters?.get("cuisineType"),
            dishType = filters?.get("dishType"),
            health = filters?.get("health")
        )

        recipeAdapter.updateRecipes(emptyList())
        binding.loadingAnimationView.visibility = View.VISIBLE
        binding.loadingTextView.visibility = View.VISIBLE
        binding.noResultsLayout.visibility = View.GONE
        binding.searchView.isEnabled = false
        binding.filterButton.isEnabled = false
        updateLoadingText()
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun updateLoadingText() {
        val fragmentReference = WeakReference(this)
        loadingTextRunnable = object : Runnable {
            override fun run() {
                val fragment = fragmentReference.get()
                if (fragment?.isAdded == true) {
                    fragment._binding?.let { binding ->
                        val randomText = fragment.loadingTexts.random()
                        binding.loadingTextView.text = randomText
                        binding.loadingTextView.alpha = 0f
                        binding.loadingTextView.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .withEndAction {
                                binding.loadingTextView.animate()
                                    .alpha(0f)
                                    .setDuration(500)
                                    .setStartDelay(2000)
                                    .withEndAction {
                                        if (fragment.isAdded) {
                                            fragment._binding?.let {
                                                this.run()
                                            }
                                        }
                                    }
                                    .start()
                            }
                            .start()
                    }
                }
            }
        }
        loadingTextHandler.post(loadingTextRunnable!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingTextHandler.removeCallbacksAndMessages(null)
        _binding = null
    }
}
