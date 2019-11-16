package com.example.foodmeals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmeals.R
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.databinding.DrinkItemsViewBinding


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class DrinkAdapter(val listener :DrinkListener) : RecyclerView.Adapter<DrinkAdapter.Holder>() {

    var meals : ArrayList<Meal> = ArrayList()

    fun setData(data: ArrayList<Meal>){
        this.meals=data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val viewBinding = DataBindingUtil.inflate<DrinkItemsViewBinding>(LayoutInflater.from(parent.context),R.layout.drink_items_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
      holder.bind(meals[position])
    }

    inner class Holder(var viewBinding: DrinkItemsViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(meal:Meal){
            viewBinding.meal=meal
            viewBinding.root.setOnClickListener {
                listener.onDrinkClick(meal)
            }
        }
    }

    interface DrinkListener{
        fun onDrinkClick(meal:Meal)
    }
}