package com.xr6sfoftware.tastyapp.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.model.Recipe

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
    ): ViewHolder {
        return   ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe : Recipe = recipeList[position]

        holder.title.text = recipe.name
        holder.image.load(recipe.thumbnailUrl)

        val emptyNutrition = (recipe.nutrition == null)
        holder.calories.text =
            if (emptyNutrition || recipe.nutrition.calories ==0)  {context.getString(R.string.recipe_list_calories) + context.getString(R.string.recipe_list_no_data)}
            else {context.getString(R.string.recipe_list_calories)  + recipe.nutrition.calories.toString()}
        holder.cookTime.text =
            if (emptyNutrition || recipe.nutrition.calories ==0)  {context.getString(R.string.recipe_list_cooktime) + context.getString(R.string.recipe_list_no_data)}
            else {context.getString(R.string.recipe_list_cooktime) + recipe.cookTime.toString()}
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

    fun setData(newRecipeList: List<Recipe>){
        val recipeListDiffUtil = RecipeListDiffUtil(recipeList, newRecipeList)
        val diffResults = DiffUtil.calculateDiff(recipeListDiffUtil)
        recipeList = newRecipeList
        diffResults.dispatchUpdatesTo(this)
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val title : TextView = v.findViewById(R.id.itemlist_title)
        val image : ImageView = v.findViewById(R.id.itemlist_image)
        val cookTime : TextView = v.findViewById(R.id.itemlist_cooktime)
        val calories : TextView = v.findViewById(R.id.itemlist_calories)
        val fibers : TextView = v.findViewById(R.id.itemlist_fibers)
    }


    class RecipeListDiffUtil(
        private val oldList : List<Recipe>,
        private val newList : List<Recipe>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when {
                oldList[oldItemPosition].id != newList[newItemPosition].id -> false
                oldList[oldItemPosition].name != newList[newItemPosition].name -> false
                oldList[oldItemPosition].seoTitle != newList[newItemPosition].seoTitle -> false
                else -> {true}
            }
        }
    }
}