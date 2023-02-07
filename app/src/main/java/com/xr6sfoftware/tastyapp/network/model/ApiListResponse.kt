package com.xr6sfoftware.tastyapp.network.model

import java.io.Serializable

data class ApiListResponse (
    val count: Int,
    val results : ArrayList<Recipe>
    )

data class Recipe (
    val nutrition: Nutrition = Nutrition(),
    val name: String = "",
    val cook_time_minutes: Double = 0.0,
    val thumbnail_url : String = "",
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

