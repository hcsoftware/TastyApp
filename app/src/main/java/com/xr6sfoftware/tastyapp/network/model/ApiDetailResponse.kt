package com.xr6sfoftware.tastyapp.network.model

data class RecipeDetail (
    val price: Price,
    val name: String,
    val description : String,
    val thumbnail_url : String,
    val prep_time_minutes: Int,
    val total_time_minutes: Int,
    val tags : ArrayList<Tags>,
    val instructions: ArrayList<Instructions>
)

data class Price (
    val total : Int,
    val portion : Int
    )

data class Tags (
    val display_name : String
    )

data class Instructions (
    val position: Int,
    val display_text: String
)
