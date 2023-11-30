package com.xr6sfoftware.tastyapp.network

import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.network.model.ApiResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceRetrofit @Inject constructor(private val apiService: RecipeApiService) {

    /**
    * loads the recipes from the API service
    * @param foodType food or term to search
    * @param cookTime String, use under_15_minutes, under_30_minutes, under_40_minutes or blank
    * @return ApiResponse with RecipeList object / Error msg
    */
    suspend fun getRecipesFromApi(foodType: String, cookTime: String): ApiResponse<RecipeList> =
        try {
            val response = apiService.getRecipes(foodType, cookTime)
            ApiResponse(null, response.body())
        } catch (e: Exception) {
            ApiResponse(e.message, null)
        }

    /**
     * loads the recipe detail given an Id from the API service
     * @param recipeId Int value recipe id
     * @return ApiResponse with RecipeDetail object / Error msg
     */
    suspend fun getRecipeFromApi(recipeId: Int): ApiResponse<RecipeDetail> =
        try {
            val response = apiService.getRecipe(recipeId)
            ApiResponse(null, response.body())
        } catch (e: Exception) {
            ApiResponse(e.message, null)
        }

}