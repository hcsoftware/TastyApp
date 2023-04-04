package com.xr6sfoftware.tastyapp.repositories

import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus

interface RecipesRepository {

    suspend fun getRecipesList(foodType: String, cookTime: String) : RepositoryStatus<RecipeList>

    suspend fun getRecipe(recipeId: Int) : RepositoryStatus<RecipeDetail>

}