package com.xr6sfoftware.tastyapp.network

import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET(NetworkConstants.RECIPES_URL)
    suspend fun getRecipes(
        @Query("q") foodType: String,
        @Query("tags") cookTime: String
    ): Response<RecipeList>

    @GET(NetworkConstants.RECIPE_DETAIL_URL)
    suspend fun getRecipe(@Query("id") recipeId: Int): Response<RecipeDetail>

}