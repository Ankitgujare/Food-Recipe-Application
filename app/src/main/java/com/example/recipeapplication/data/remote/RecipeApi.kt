package com.example.recipeapplication.data.remote

import com.example.recipeapplication.data.dto.Recipe
import com.example.recipeapplication.data.dto.Recipes
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): Recipes

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe
}
