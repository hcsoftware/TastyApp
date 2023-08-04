package com.xr6sfoftware.tastyapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6sfoftware.tastyapp.model.RecipeList
import com.xr6sfoftware.tastyapp.repositories.RecipesRepository
import com.xr6sfoftware.tastyapp.repositories.model.RepositoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * viewModel provides data from the Repo to the view (searchFragment), so its not affected by the view lifecycle
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

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

            when (val result = recipesRepository.getRecipesList(foodType, cookTime)) {
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