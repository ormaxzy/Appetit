package com.example.appetit.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appetit.databinding.FragmentFiltersBinding
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import com.example.appetit.R
import com.example.appetit.presentation.viewmodels.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!
    private val filtersViewModel: FiltersViewModel by activityViewModels()

    private val anyString by lazy { getString(R.string.any) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupApplyButton()
        setupClearButton()
    }

    private fun setupSpinners() {
        val cuisineTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cuisine_types,
            R.layout.custom_spinner_item
        )
        cuisineTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.cuisineTypeSpinner.adapter = cuisineTypeAdapter

        val dishTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dish_types,
            R.layout.custom_spinner_item
        )
        dishTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.dishTypeSpinner.adapter = dishTypeAdapter

        val healthAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.health_labels,
            R.layout.custom_spinner_item
        )
        healthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.healthSpinner.adapter = healthAdapter
    }

    private fun setupApplyButton() {
        binding.applyButton.setOnClickListener {
            val selectedFilters = mutableMapOf<String, List<String>>()

            val selectedMealType = when (binding.mealTypeRadioGroup.checkedRadioButtonId) {
                R.id.radio_breakfast -> getString(R.string.breakfast)
                R.id.radio_dinner -> getString(R.string.dinner)
                R.id.radio_lunch -> getString(R.string.lunch)
                R.id.radio_snack -> getString(R.string.snack)
                R.id.radio_teatime -> getString(R.string.teatime)
                else -> anyString
            }

            val selectedCuisineType = binding.cuisineTypeSpinner.selectedItem.toString()
            val selectedDishType = binding.dishTypeSpinner.selectedItem.toString()
            val selectedHealth = binding.healthSpinner.selectedItem.toString()

            if (selectedMealType != anyString) selectedFilters["mealType"] =
                listOf(selectedMealType)
            if (selectedCuisineType != anyString) selectedFilters["cuisineType"] =
                listOf(selectedCuisineType)
            if (selectedDishType != anyString) selectedFilters["dishType"] =
                listOf(selectedDishType)
            if (selectedHealth != anyString) selectedFilters["health"] = listOf(selectedHealth)

            filtersViewModel.setFilters(selectedFilters)

            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_right)
                .setPopEnterAnim(R.anim.slide_in_right)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()

            findNavController().navigate(
                R.id.action_filtersFragment_to_navigation_search,
                null,
                navOptions
            )
        }
    }

    private fun setupClearButton() {
        binding.clearButton.setOnClickListener {
            binding.mealTypeRadioGroup.clearCheck()
            binding.cuisineTypeSpinner.setSelection(0)
            binding.dishTypeSpinner.setSelection(0)
            binding.healthSpinner.setSelection(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
