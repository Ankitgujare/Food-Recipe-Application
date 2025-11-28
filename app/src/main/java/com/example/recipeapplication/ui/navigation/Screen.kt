package com.example.recipeapplication.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{recipeId}") {
        fun createRoute(recipeId: Int) = "details/$recipeId"
    }
    object Bookmarks : Screen("bookmarks")
}
