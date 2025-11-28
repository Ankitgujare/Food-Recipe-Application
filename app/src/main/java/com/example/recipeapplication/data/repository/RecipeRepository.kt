package com.example.recipeapplication.data.repository

import com.example.recipeapplication.data.dto.Recipe
import com.example.recipeapplication.data.remote.RetrofitInstance
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object RecipeRepository {
    private val api = RetrofitInstance.api
    private val bookmarks = mutableListOf<Recipe>()
    private val recentlyViewed = mutableListOf<Recipe>()
    private val mutex = Mutex()

    suspend fun getRecipes(): List<Recipe> {
        return try {
            api.getRecipes().recipes
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getRecipe(id: Int): Recipe? {
        return try {
            val recipe = api.getRecipe(id)
            if (recipe != null) {
                addToRecentlyViewed(recipe)
            }
            recipe
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun addToRecentlyViewed(recipe: Recipe) {
        mutex.withLock {
            recentlyViewed.removeAll { it.id == recipe.id }
            recentlyViewed.add(0, recipe)
            if (recentlyViewed.size > 10) {
                recentlyViewed.removeAt(recentlyViewed.lastIndex)
            }
        }
    }

    suspend fun getRecentlyViewed(): List<Recipe> {
        return mutex.withLock {
            recentlyViewed.toList()
        }
    }

    suspend fun toggleBookmark(recipe: Recipe) {
        mutex.withLock {
            if (bookmarks.any { it.id == recipe.id }) {
                bookmarks.removeAll { it.id == recipe.id }
            } else {
                bookmarks.add(recipe)
            }
        }
    }

    suspend fun isBookmarked(id: Int): Boolean {
        return mutex.withLock {
            bookmarks.any { it.id == id }
        }
    }

    suspend fun getBookmarks(): List<Recipe> {
        return mutex.withLock {
            bookmarks.toList()
        }
    }
}
