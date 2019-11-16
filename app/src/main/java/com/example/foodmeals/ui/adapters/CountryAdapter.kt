package com.example.foodmeals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmeals.R
import com.example.foodmeals.data.models.CountryModel
import com.example.foodmeals.databinding.CountryItemsViewBinding


/*
* Created By mabrouk on 26/09/19
* Cook Meals
*/

class CountryAdapter(val listener:CountryListener) : RecyclerView.Adapter<CountryAdapter.Holder>() {

    var coutries:ArrayList<CountryModel> = ArrayList()

    fun setData(data:ArrayList<CountryModel>){
        coutries=data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = coutries.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bind(coutries[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding=DataBindingUtil.inflate<CountryItemsViewBinding>(LayoutInflater.from(parent.context), R.layout.country_items_view,parent,false)
        return Holder(viewBinding)
    }

    inner class Holder(var viewBinding:CountryItemsViewBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(country:CountryModel){
            viewBinding.country=country
            viewBinding.root.setOnClickListener {
                listener.onCountryClick(country)
            }
        }
    }

    interface CountryListener{
        fun onCountryClick(country:CountryModel)
    }
}