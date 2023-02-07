package com.xr6sfoftware.tastyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6sfoftware.tastyapp.network.ApiServiceImp
import com.xr6sfoftware.tastyapp.network.Callback
import com.xr6sfoftware.tastyapp.network.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var apiServiceImp: ApiServiceImp
    private val uiSearchFragmentState = MutableLiveData<UiSearchFragmentState>()
    fun getUiSearchFragmentState() = uiSearchFragmentState

    fun getRecipeList(
        foodType: String,
        cookTime: String
    ) {
        uiSearchFragmentState.value = UiSearchFragmentState.Loading
        apiServiceImp.getRecipesList(
            foodType,
            cookTime,
            object : Callback<List<Recipe>> {
                override fun onSuccess(result: List<Recipe>) {
                    uiSearchFragmentState.value = UiSearchFragmentState.Recipes(result)
                }
                override fun onFailure(exception: Exception) {
                    uiSearchFragmentState.value = UiSearchFragmentState.Error(exception.toString())
                }
            }
        )
    }

    sealed class UiSearchFragmentState {
        object Loading : UiSearchFragmentState()
        data class Error(val error: String) : UiSearchFragmentState()
        data class Recipes(val recipeList: List<Recipe>) : UiSearchFragmentState()
    }

}