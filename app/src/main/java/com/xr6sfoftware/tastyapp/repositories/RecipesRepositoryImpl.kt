package com.xr6sfoftware.tastyapp.repositories

import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.network.ApiServiceRetrofit
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus
import javax.inject.Inject

/**
 * Implements the Repository Functions. Interacts with Api Service.
 */
class RecipesRepositoryImpl @Inject constructor(
   private val apiService: ApiServiceRetrofit
) : RecipesRepository {


    /**
     *  loads the recipes from the API service
     *  @param foodType food or term to search
     *  @param cookTime String, use under_15_minutes, under_30_minutes, under_40_minutes or blank
     */

    override suspend fun getRecipesList(foodType: String, cookTime: String) : RepositoryStatus<RecipeList> {

        val apiResponse = apiService.getRecipesFromApi(foodType, cookTime)
        return if (apiResponse.error.isNullOrBlank()) {
            RepositoryStatus.Success<RecipeList>(apiResponse.values)
        } else {
            RepositoryStatus.Failed(apiResponse.error)
        }


    }

    /**
     *  loads the recipe detail given an Id from the API service
     *  @param recipeId Int value recipe id
     */
    override suspend fun getRecipe(recipeId: Int): RepositoryStatus<RecipeDetail> {


        val apiResponse = apiService.getRecipeFromApi(recipeId)
        return if (apiResponse.error.isNullOrBlank()) {
            RepositoryStatus.Success<RecipeDetail>(apiResponse.values)
        } else {
            RepositoryStatus.Failed(apiResponse.error)
        }

    }

}