package com.example.foodmeals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmeals.R
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.databinding.MealsItemsViewBinding


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class FilterMealsAdapter (val listener :MealListener) : RecyclerView.Adapter<FilterMealsAdapter.Holder>() {

    var meals: ArrayList<Meal> = ArrayList()
    var width:Int=0

    fun setData(data: ArrayList<Meal>) {
        this.meals = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding = DataBindingUtil.inflate<MealsItemsViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.meals_items_view, parent, false
        )
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(meals[position])
    }

    inner class Holder(var viewBinding: MealsItemsViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(meal: Meal) {
            viewBinding.root.post {
                this@FilterMealsAdapter.width=viewBinding.root.width
            }
            viewBinding.meal = meal
            viewBinding.root.setOnClickListener {
                listener.onMealClick(meal)
            }
        }
    }

    interface MealListener {
        fun onMealClick(meal: Meal)
    }
}