package com.xr6sfoftware.tastyapp.view.adapters

import com.xr6sfoftware.tastyapp.network.model.Recipe

interface RecipeListAdapterClickListener {

    fun onClick(recipe: Recipe)

}