package com.xr6sfoftware.tastyapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.repositories.RecipesRepository
import com.xr6sfoftware.tastyapp.repositories.RecipesRepositoryImpl
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * viewModel provides data from the Repo to the view (recipeFragment), so its not affected by the view lifecycle
 */
@HiltViewModel
class RecipeViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var recipesRepository: RecipesRepository
    private val uiRecipeFragmentState = MutableLiveData<UiRecipeFragmentState>()
    fun getUiRecipeFragmentState() = uiRecipeFragmentState

    fun getRecipeDetail(recipeId: Int) {

        uiRecipeFragmentState.value = UiRecipeFragmentState.Loading
        viewModelScope.launch {

            when (val result = recipesRepository.getRecipe(recipeId)) {
                is RepositoryStatus.Success<RecipeDetail> -> {
                    uiRecipeFragmentState.postValue(UiRecipeFragmentState.Recipe(result.data!!))
                }
                is RepositoryStatus.Failed -> {
                    uiRecipeFragmentState.postValue(UiRecipeFragmentState.Error(result.message!!))
                }
            }
        }

    }


    sealed class UiRecipeFragmentState {
        object Loading : UiRecipeFragmentState()
        data class Error(val error: String) : UiRecipeFragmentState()
        data class Recipe(val recipe: RecipeDetail) : UiRecipeFragmentState()
    }

}