package com.xr6sfoftware.tastyapp.repositories

import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus

/**
 * This interface defines the methods the repository should implement to provide data to the viewModel.
 */
interface RecipesRepository {

    suspend fun getRecipesList(foodType: String, cookTime: String) : RepositoryStatus<RecipeList>

    suspend fun getRecipe(recipeId: Int) : RepositoryStatus<RecipeDetail>

}