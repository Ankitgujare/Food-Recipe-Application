package com.example.recipeapplication.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapplication.data.dto.Recipe
import com.example.recipeapplication.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val repository = RecipeRepository

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    fun fetchRecipe(id: Int) {
        viewModelScope.launch {
            val fetchedRecipe = repository.getRecipe(id)
            _recipe.value = fetchedRecipe
            _isBookmarked.value = repository.isBookmarked(id)
        }
    }

    fun toggleBookmark() {
        _recipe.value?.let {
            viewModelScope.launch {
                repository.toggleBookmark(it)
                _isBookmarked.value = repository.isBookmarked(it.id)
            }
        }
    }
}
