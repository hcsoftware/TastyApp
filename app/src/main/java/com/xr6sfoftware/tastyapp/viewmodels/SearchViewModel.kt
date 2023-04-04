package com.xr6sfoftware.tastyapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6sfoftware.tastyapp.model.Recipe
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.repositories.RecipesRepositoryImpl
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var recipesRepositoryImpl: RecipesRepositoryImpl
    private val uiSearchFragmentState = MutableLiveData<UiSearchFragmentState>()
    fun getUiSearchFragmentState() = uiSearchFragmentState

    /**
     *  calls the repository for recipes
     *  @param foodType food or term to search
     *  @param cookTime String, use under_15_minutes, under_30_minutes, under_40_minutes or blank
     */
    fun getRecipes(foodType: String, cookTime: String) {
        uiSearchFragmentState.value = UiSearchFragmentState.Loading
        viewModelScope.launch {

            when (val result = recipesRepositoryImpl.getRecipesList(foodType, cookTime)) {
                is RepositoryStatus.Success<RecipeList> -> {
                    uiSearchFragmentState.postValue(UiSearchFragmentState.Recipes(result.data!!))
                }
                is RepositoryStatus.Failed -> {
                    uiSearchFragmentState.postValue(UiSearchFragmentState.Error(result.message!!))
                }
                }
            }
        }

        sealed class UiSearchFragmentState {
            object Loading : UiSearchFragmentState()
            data class Error(val error: String) : UiSearchFragmentState()
            data class Recipes(val recipeList: RecipeList) : UiSearchFragmentState()
        }

    }