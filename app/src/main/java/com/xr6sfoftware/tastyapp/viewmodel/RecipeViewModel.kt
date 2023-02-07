package com.xr6sfoftware.tastyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6sfoftware.tastyapp.network.ApiServiceImp
import com.xr6sfoftware.tastyapp.network.Callback
import com.xr6sfoftware.tastyapp.network.model.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var apiServiceImp: ApiServiceImp
    private val uiRecipeFragmentState = MutableLiveData<UiRecipeFragmentState>()
    fun getUiRecipeFragmentState() = uiRecipeFragmentState

    fun getRecipeDetail(
        recipeId: Int)
        {
        uiRecipeFragmentState.value = UiRecipeFragmentState.Loading
        apiServiceImp.getRecipeDetail(
            recipeId,
            object : Callback<RecipeDetail> {
                override fun onSuccess(result: RecipeDetail) {
                    uiRecipeFragmentState.value = UiRecipeFragmentState.Recipe(result)
                }

                override fun onFailure(exception: Exception) {
                    uiRecipeFragmentState.value = UiRecipeFragmentState.Error(exception.toString())
                }

            }
        )
    }


    sealed class UiRecipeFragmentState {
        object Loading : UiRecipeFragmentState()
        data class Error(val error: String) : UiRecipeFragmentState()
        data class Recipe(val recipe: RecipeDetail) : UiRecipeFragmentState()
    }


}