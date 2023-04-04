package com.xr6sfoftware.tastyapp.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class RecipeDetail (
    val price: Price,
    val name: String,
    val description : String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl : String,
    @SerializedName("prep_time_minutes")
    val prepTime: Int,
    @SerializedName("total_time_minutes")
    val totalTime: Int,
    val tags : ArrayList<Tags>,
    val instructions: ArrayList<Instructions>
)

data class Price (
    val total : Int,
    val portion : Int
    )

data class Tags (
    @SerializedName("display_name")
    val displayName : String
    )

data class Instructions (
    val position: Int,
    @SerializedName("display_text")
    val displayText: String
)

fun parseResponseToRecipeDetail(response: String): RecipeDetail = Gson().fromJson(response, RecipeDetail::class.java)