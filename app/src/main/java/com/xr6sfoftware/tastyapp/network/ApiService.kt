package com.xr6sfoftware.tastyapp.network

import com.xr6sfoftware.tastyapp.network.model.Recipe
import com.xr6sfoftware.tastyapp.network.model.RecipeDetail

interface ApiService {

    fun getRecipesList(foodType: String, cookTime: String, callback: Callback<List<Recipe>>)

    fun getRecipeDetail(recipeId: Int, callback: Callback<RecipeDetail>)

}