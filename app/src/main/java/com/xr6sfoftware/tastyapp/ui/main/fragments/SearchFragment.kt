package com.xr6sfoftware.tastyapp.ui.main.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.databinding.SearchFragmentBinding
import com.xr6sfoftware.tastyapp.model.Recipe
import com.xr6sfoftware.tastyapp.ui.main.adapters.RecipeListAdapter
import com.xr6sfoftware.tastyapp.utils.hideKeyboard
import com.xr6sfoftware.tastyapp.utils.validateUserInput
import com.xr6sfoftware.tastyapp.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
@author Hernán Carrera
@version 1.0
This Fragment searchs and list recipes given a term.
 */

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: SearchFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeListAdapter: RecipeListAdapter

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false)

        recyclerView = viewBinding.searchFragmentRecyclerView
        recipeListAdapter = RecipeListAdapter(
            { recipe ->
                onClick(recipe)
            },
            requireContext()
        )
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = recipeListAdapter
        }

        viewBinding.radioGroup.check(viewBinding.radioButton4.id)

        setClickListeners()
        setObservers()
        return viewBinding.root
    }

    private fun setObservers() {

        viewModel.getUiSearchFragmentState().observe(viewLifecycleOwner) {

            when (it) {

                SearchViewModel.UiSearchFragmentState.Loading -> {
                    viewBinding.searchFragmentLoading.visibility = View.VISIBLE
                }
                is SearchViewModel.UiSearchFragmentState.Error -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    showErrorDialog(it.error)
                }
                is SearchViewModel.UiSearchFragmentState.Recipes -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    viewBinding.searchFragmentEmptyImg.visibility = View.GONE
                    viewBinding.searchFragmentEmptyTxt.visibility = View.GONE
                    if (it.recipeList.count > 0) {
                        recipeListAdapter.setData(it.recipeList.results)
                    } else {
                        showIncorrectInputMsg(resources.getString(R.string.search_frag_error_msg_no_results))
                    }

                }
            }

        }

    }

    private fun showErrorDialog(error: String) {
        val mBuilder = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.search_frag_error_title))
            .setMessage(error)
            .setPositiveButton(resources.getString(R.string.search_frag_error_btn), null)
            .show()
        mBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            mBuilder.dismiss()
        }
    }

    private fun setClickListeners() {

        viewBinding.searchFragmentInputText.imeOptions = EditorInfo.IME_ACTION_GO
        viewBinding.searchFragmentInputText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                validateAndSearchRecipes()
                true
            } else false
        })
        viewBinding.searchFragmentSearchButton.setOnClickListener {
            validateAndSearchRecipes()
        }

    }

    private fun validateAndSearchRecipes() {
        hideKeyboard()
        if (viewBinding.searchFragmentInputText.validateUserInput()) {
            viewModel.getRecipes(
                viewBinding.searchFragmentInputText.text.toString(),
                getCookTime()
            )
        } else {
            showIncorrectInputMsg(resources.getString(R.string.search_frag_error_msg_wrong))
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

    private fun onClick(recipe: Recipe) {
        val bundle: Bundle = bundleOf("recipeItem" to recipe)
        findNavController().navigate(R.id.recipeFragment, bundle)
    }


}