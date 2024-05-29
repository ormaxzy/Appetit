package com.example.appetit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor() : ViewModel() {

    private val _filters = MutableLiveData<Map<String, List<String>>>()
    val filters: LiveData<Map<String, List<String>>> = _filters

    fun setFilters(filters: Map<String, List<String>>) {
        _filters.value = filters
    }
}
