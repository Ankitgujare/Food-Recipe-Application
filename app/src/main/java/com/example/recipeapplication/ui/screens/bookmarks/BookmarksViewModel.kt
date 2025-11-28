package com.example.recipeapplication.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapplication.data.dto.Recipe
import com.example.recipeapplication.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarksViewModel : ViewModel() {
    private val repository = RecipeRepository

    private val _bookmarks = MutableStateFlow<List<Recipe>>(emptyList())
    val bookmarks: StateFlow<List<Recipe>> = _bookmarks.asStateFlow()

    private val _recentlyViewed = MutableStateFlow<List<Recipe>>(emptyList())
    val recentlyViewed: StateFlow<List<Recipe>> = _recentlyViewed.asStateFlow()

    fun fetchBookmarks() {
        viewModelScope.launch {
            _bookmarks.value = repository.getBookmarks()
            _recentlyViewed.value = repository.getRecentlyViewed()
        }
    }
}
