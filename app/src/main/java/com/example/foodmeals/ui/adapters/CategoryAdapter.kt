package com.example.foodmeals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmeals.R
import com.example.foodmeals.data.models.Category
import com.example.foodmeals.databinding.CategoryItemsViewBinding


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class CategoryAdapter(val listener:CategoryListener) : RecyclerView.Adapter<CategoryAdapter.Holder>(){
    var categories:ArrayList<Category> = ArrayList()

    fun setData(data:ArrayList<Category>){
        categories=data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
     val viewBinding= DataBindingUtil.inflate<CategoryItemsViewBinding>(LayoutInflater.from(parent.context), R.layout.category_items_view,parent,false)
        return Holder(viewBinding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bind(category = categories[position])
    }

    inner class Holder(var viewBinding: CategoryItemsViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(category:Category){
            viewBinding.category=category
            viewBinding.root.setOnClickListener { listener.onCategoryClick(category) }
        }
    }

    interface  CategoryListener{
        fun onCategoryClick(category: Category)
    }
}