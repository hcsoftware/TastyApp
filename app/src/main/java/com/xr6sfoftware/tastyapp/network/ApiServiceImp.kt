package com.xr6sfoftware.tastyapp.network

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.xr6sfoftware.tastyapp.network.model.ApiListResponse
import com.xr6sfoftware.tastyapp.network.model.Recipe
import com.xr6sfoftware.tastyapp.network.model.RecipeDetail
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@InstallIn(SingletonComponent::class)
@Module
class ApiServiceImp @Inject constructor(@ApplicationContext val context: Context) : ApiService {

    private val rapidApiKey: String = "7a7139a7ddmshffc2d74e52712e6p1341bfjsn4e975643f271"
    private val rapidApiHost: String = "tasty.p.rapidapi.com"
    private val requestQueue = Volley.newRequestQueue(context)
    private val recipeListUrl = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=30"
    private val recipeDetailUrl = "https://tasty.p.rapidapi.com/recipes/get-more-info?id="

    /**
     * loads a list of recipes from the api service
     * @param foodType String food or term to search
     * @param cookTime String cooktime tag to search
     * @param callback returns recipe list onSuccess and error onFailure
     */
    override fun getRecipesList(
        foodType: String,
        cookTime: String,
        callback: Callback<List<Recipe>>
    ) {

        val req: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            if (cookTime.isNullOrEmpty()) {"$recipeListUrl&q=$foodType"} else {"$recipeListUrl&tags=$cookTime&q=$foodType"},
            null,
            Response.Listener { response ->
                callback.onSuccess(
                    Gson().fromJson(response.toString(), ApiListResponse::class.java).results
                )
            },
            Response.ErrorListener { error ->
                callback.onFailure(error)
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
     * loads the details of recipe from the api service
     * @param recipeId Int recipe_Id
     * @param callback returns recipe item onSuccess and error onFailure
     */
    override fun getRecipeDetail(recipeId: Int, callback: Callback<RecipeDetail>) {

            val req: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET,
                "$recipeDetailUrl$recipeId",
                null,
                Response.Listener { response ->
                    callback.onSuccess(
                        Gson().fromJson(response.toString(), RecipeDetail::class.java)
                    )
                },
                Response.ErrorListener { error ->
                    callback.onFailure(error)
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