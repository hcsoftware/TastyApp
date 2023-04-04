package com.xr6sfoftware.tastyapp.ui.main.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.databinding.RecipeFragmentBinding
import com.xr6sfoftware.tastyapp.model.Recipe
import com.xr6sfoftware.tastyapp.model.RecipeDetail
import com.xr6sfoftware.tastyapp.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
@author Hernán Carrera
@version 1.0
This Fragment shows a given Recipe on detail.
 Gets recipeId from bundle args and loads Data from viewModel
 */
@AndroidEntryPoint
class RecipeFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeFragment()
    }

    private lateinit var recipe : Recipe
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var viewBinding: RecipeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = RecipeFragmentBinding.inflate(inflater, container, false)
        recipe = arguments?.getSerializable("recipeItem") as Recipe

        viewModel.getRecipeDetail(recipe.id)

        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setObservers()

    }

    private fun setObservers() {
        viewModel.getUiRecipeFragmentState().observe(viewLifecycleOwner) {
            when (it) {
                RecipeViewModel.UiRecipeFragmentState.Loading -> {
                    viewBinding.recipeFragmentLoading.visibility = View.VISIBLE
                }
                is RecipeViewModel.UiRecipeFragmentState.Error -> {
                    viewBinding.recipeFragmentLoading.visibility = View.GONE
                    showErrorMessage()
                }
                is RecipeViewModel.UiRecipeFragmentState.Recipe -> {
                    viewBinding.recipeFragmentLoading.visibility = View.GONE
                    loadRecipeOnViews(it.recipe)
                }
            }
        }
    }

    private fun showErrorMessage() {
        val mBuilder = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.recipe_frag_error_title))
            .setMessage(resources.getString(R.string.recipe_frag_error_msg))
            .setPositiveButton(resources.getString(R.string.recipe_frag_error_btn), null)
            .show()
        mBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            mBuilder.dismiss()
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun loadRecipeOnViews(recipeDetail: RecipeDetail) {

        viewBinding.recipeFragmentImage.load(recipeDetail.thumbnailUrl)
        viewBinding.recipeFragmentTitle.text = recipeDetail.name
        if (!recipeDetail.description.isNullOrEmpty()) {
            viewBinding.recipeFragmentDesc.text = recipeDetail.description
            viewBinding.recipeFragmentDesc.visibility = View.VISIBLE
        }

        viewBinding.recipeFragmentPrepTime.text =
            if (recipeDetail.prepTime == 0) {resources.getString(R.string.recipe_frag_prep_time) + resources.getString(R.string.recipe_frag_no_data) }
            else {resources.getString(R.string.recipe_frag_prep_time) + recipeDetail.prepTime.toString()}
        viewBinding.recipeFragmentTotalTime.text =
            if (recipeDetail.totalTime == 0) {resources.getString(R.string.recipe_frag_total_time) + resources.getString(R.string.recipe_frag_no_data) }
            else {resources.getString(R.string.recipe_frag_total_time) + recipeDetail.totalTime.toString()}
        viewBinding.recipeFragmentTotalPrice.text =
            if ((recipeDetail.price == null)  || (recipeDetail.price.total == 0)) { resources.getString(R.string.recipe_frag_total_price) + resources.getString(R.string.recipe_frag_no_data) }
            else {resources.getString(R.string.recipe_frag_total_price)  + recipeDetail.price.total.toString() }
        viewBinding.recipeFragmentPortionPrice.text =
            if ((recipeDetail.price == null)  || (recipeDetail.price.portion == 0)) { resources.getString(R.string.recipe_frag_portion_price) + resources.getString(R.string.recipe_frag_no_data) }
            else {resources.getString(R.string.recipe_frag_portion_price) + recipeDetail.price.portion.toString()}

        var tags : String = "TAGS = "
        recipeDetail.tags.forEach {
            tags = tags + " | ${it.displayName}"
        }
        viewBinding.recipeFragmentTags.text = tags

        var instructions : String = ""
        recipeDetail.instructions.forEach {
            instructions = instructions + " \u2022 ${it.displayText} \n"
        }
        viewBinding.recipeFragmentInstructions.text = instructions

    }

}