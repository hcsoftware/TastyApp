package com.xr6sfoftware.tastyapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.network.model.Recipe
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * Custom RecyclerView Adapter to show Recipe List in Search Fragment
 */
class RecipeListAdapter (
    private val clickListener: RecipeListAdapterClickListener,
    private val context : Context
    ): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    private var recipeList = listOf<Recipe>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListAdapter.ViewHolder {
        return   ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        val recipe : Recipe = recipeList[position]

        holder.title.text = recipe.name
        holder.image.load(recipe.thumbnail_url)

        val emptyNutrition = recipe.nutrition == null
        holder.calories.text =
            if (emptyNutrition || recipe.nutrition.calories ==0)  {context.getString(R.string.recipe_list_calories) + context.getString(R.string.recipe_list_no_data)}
            else {context.getString(R.string.recipe_list_calories)  + recipe.nutrition.calories.toString()}
        holder.cookTime.text =
            if (emptyNutrition || recipe.nutrition.calories ==0)  {context.getString(R.string.recipe_list_cooktime) + context.getString(R.string.recipe_list_no_data)}
            else {context.getString(R.string.recipe_list_cooktime) + recipe.cook_time_minutes.toString()}
        holder.fibers.text =
            if (emptyNutrition || recipe.nutrition.calories ==0) {context.getString(R.string.recipe_list_fibers) + context.getString(R.string.recipe_list_no_data)}
            else {context.getString(R.string.recipe_list_fibers) + recipe.nutrition.fiber.toString()}

        holder.itemView.setOnClickListener {
            clickListener.onClick(recipe)
        }

    }

    override fun getItemCount() = recipeList.size

    fun updateDataOnView(recipes: List<Recipe>) {
        recipeList = recipes
        notifyDataSetChanged()
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val title : TextView = v.findViewById(R.id.itemlist_title)
        val image : ImageView = v.findViewById(R.id.itemlist_image)
        val cookTime : TextView = v.findViewById(R.id.itemlist_cooktime)
        val calories : TextView = v.findViewById(R.id.itemlist_calories)
        val fibers : TextView = v.findViewById(R.id.itemlist_fibers)
    }

}