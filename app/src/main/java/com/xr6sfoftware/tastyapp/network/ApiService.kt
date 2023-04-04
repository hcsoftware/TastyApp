package com.xr6sfoftware.tastyapp.network

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.model.parseResponseToRecipeDetail
import com.xr6sfoftware.tastyapp.model.parseResponseToRecipeList
import com.xr6sfoftware.tastyapp.network.model.ApiResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@InstallIn(SingletonComponent::class)
@Module
class ApiService @Inject constructor(@ApplicationContext val context: Context) {

    private val requestQueue = Volley.newRequestQueue(context)

    private val rapidApiKey: String = NetworkConstants.RAPID_API_KEY
    private val rapidApiHost: String = NetworkConstants.RAPID_API_HOST
    private val baseUrl = NetworkConstants.BASE_URL
    private val recipeListUrl = NetworkConstants.URL_RECIPE_LIST
    private val recipeDetailUrl = NetworkConstants.URL_RECIPE_DETAIL

    /**
     *  loads the recipes from the API service
     *  @param foodType food or term to search
     *  @param cookTime String, use under_15_minutes, under_30_minutes, under_40_minutes or blank
     */
    suspend fun getRecipesFromApi(foodType: String, cookTime: String) : ApiResponse<RecipeList> =
        suspendCoroutine { continuation  ->
            var apiResponse : ApiResponse <RecipeList>
            val req: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET,
                if (cookTime.isNullOrEmpty()) {"$baseUrl$recipeListUrl&q=$foodType"} else {"$baseUrl$recipeListUrl&tags=$cookTime&q=$foodType"},
                null,
                Response.Listener { response ->
                    apiResponse = ApiResponse(null, parseResponseToRecipeList(response.toString()))
                    continuation.resume(apiResponse)
                },
                Response.ErrorListener { error ->
                    apiResponse = ApiResponse(error.message.toString(), null)
                    //to avoid this could use resumeWithException and use try/catch in the invoke method
                    continuation.resume(apiResponse)
                }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["X-RapidAPI-Key"] = rapidApiKey
                    headers["X-RapidAPI-Host"] = rapidApiHost
                    return headers
                }
            }
            requestQueue.add(req)
        }

    /**
     *  loads the recipe detail given an Id from the API service
     *  @param recipeId Int value recipe id
     */
    suspend fun getRecipeFromApi(recipeId: Int): ApiResponse<RecipeDetail> =
        suspendCoroutine { continuation ->
            var apiResponse : ApiResponse <RecipeDetail>
            val req: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET,
                "$baseUrl$recipeDetailUrl$recipeId",
                null,
                Response.Listener { response ->
                    apiResponse = ApiResponse(null, parseResponseToRecipeDetail(response.toString()))
                    continuation.resume(apiResponse)
                },
                Response.ErrorListener { error ->
                    apiResponse = ApiResponse(error.message.toString(), null)
                    //to avoid this could use resumeWithException and use try/catch in the invoke method
                    continuation.resume(apiResponse)
                }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["X-RapidAPI-Key"] = rapidApiKey
                    headers["X-RapidAPI-Host"] = rapidApiHost
                    return headers
                }
            }
            requestQueue.add(req)
        }

}