package com.xr6sfoftware.tastyapp.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeList (
    val count: Int,
    val results : ArrayList<Recipe>
    )

data class Recipe (
    val nutrition: Nutrition = Nutrition(),
    val name: String = "",
    @SerializedName("cook_time_minutes")
    val cookTime: Double = 0.0,
    @SerializedName("thumbnail_url")
    val thumbnailUrl : String = "",
    val seoTitle: String = "",
    val id: Int = 0
    ) : Serializable

data class Nutrition (
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val sugar: Int = 0,
    val carbohydrates: Int = 0,
    val fiber: Int = 0
    )

fun parseResponseToRecipeList(response: String): RecipeList = Gson().fromJson(response, RecipeList::class.java)

