package com.xr6sfoftware.tastyapp.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.databinding.SearchFragmentBinding
import com.xr6sfoftware.tastyapp.network.model.Recipe
import com.xr6sfoftware.tastyapp.utils.hideKeyboard
import com.xr6sfoftware.tastyapp.utils.validateUserInput
import com.xr6sfoftware.tastyapp.viewmodel.SearchViewModel
import com.xr6sfoftware.tastyapp.view.adapters.RecipeListAdapter
import com.xr6sfoftware.tastyapp.view.adapters.RecipeListAdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
@author Hernán Carrera
@version 1.0
This Fragment list recipes from a search term.
 */

@AndroidEntryPoint
class SearchFragment : Fragment(), RecipeListAdapterClickListener {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: SearchFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val recipeListAdapter: RecipeListAdapter by lazy {
        RecipeListAdapter(this, requireContext())
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false)

        recyclerView = viewBinding.searchFragmentRecyclerView
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = recipeListAdapter
        }

        viewBinding.radioGroup.check(viewBinding.radioButton4.id)

        setClickListeners()
        setObservers()
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun setObservers() {

        viewModel.getUiSearchFragmentState().observe(viewLifecycleOwner) {

            when (it) {

                SearchViewModel.UiSearchFragmentState.Loading -> {
                    viewBinding.searchFragmentLoading.visibility = View.VISIBLE
                }
                is SearchViewModel.UiSearchFragmentState.Error -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE

                }
                is SearchViewModel.UiSearchFragmentState.Recipes -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    if (it.recipeList.isNotEmpty()) {
                        recipeListAdapter.updateDataOnView(it.recipeList)
                    } else {
                        showIncorrectInputMsg(resources.getString(R.string.search_frag_error_msg_no_results))
                    }

                }
            }

        }

    }

    private fun setClickListeners() {


        viewBinding.searchFragmentSearchButton.setOnClickListener {

            if (viewBinding.searchFragmentInputText.validateUserInput()) {

                viewModel.getRecipeList(
                    viewBinding.searchFragmentInputText.text.toString(),
                    getCookTime()
                )
                hideKeyboard()

            } else {
                showIncorrectInputMsg(resources.getString(R.string.search_frag_error_msg_wrong))
            }
        }
    }

    private fun showIncorrectInputMsg(msg: String) {

        val mBuilder = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.search_frag_error_title))
            .setMessage(msg)
            .setPositiveButton(resources.getString(R.string.search_frag_error_btn), null)
            .show()
        mBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            mBuilder.dismiss()
        }

    }


    private fun getCookTime(): String {

        when (viewBinding.radioGroup.checkedRadioButtonId) {
            viewBinding.radioButton.id -> {
                return "under_15_minutes"
            }
            viewBinding.radioButton2.id -> {
                return "under_30_minutes"
            }
            viewBinding.radioButton3.id -> {
                return "under_45_minutes"
            }
        }
        return ""
    }

    override fun onClick(recipe: Recipe) {
        val bundle: Bundle = bundleOf("recipeItem" to recipe)
        findNavController().navigate(R.id.recipeFragment, bundle)
    }

}