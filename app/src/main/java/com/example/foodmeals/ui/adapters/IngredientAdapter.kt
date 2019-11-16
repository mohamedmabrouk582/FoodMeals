package com.example.foodmeals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmeals.R
import com.example.foodmeals.data.models.Ingredient
import com.example.foodmeals.databinding.IngredientItemsViewBinding


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class IngredientAdapter(val listener:IngredientListener) : RecyclerView.Adapter<IngredientAdapter.Holder>() {
    var ingredients:ArrayList<Ingredient> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding= DataBindingUtil.inflate<IngredientItemsViewBinding>(LayoutInflater.from(parent.context), R.layout.ingredient_items_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
      holder.bind(ingredient = ingredients[position])
    }

    fun setData(data:ArrayList<Ingredient>){
        ingredients=data
        notifyDataSetChanged()
    }

    inner class Holder(var viewBinding: IngredientItemsViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(ingredient: Ingredient) {
            viewBinding.ingerdient=ingredient
            viewBinding.root.setOnClickListener { listener.onIngredientClick(ingredient) }
        }
    }

    interface  IngredientListener{
        fun onIngredientClick(ingredient:Ingredient)
    }
}