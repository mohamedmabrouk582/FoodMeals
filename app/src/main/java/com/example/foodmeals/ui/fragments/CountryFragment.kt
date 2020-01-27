package com.example.foodmeals.ui.fragments

import androidx.databinding.DataBindingUtil
import com.example.foodmeals.R
import com.example.foodmeals.data.models.CountryModel
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.databinding.CountriesLayoutBinding
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.ui.adapters.CountryAdapter
import com.example.foodmeals.ui.base.BaseDialogFragment
import com.example.foodmeals.utils.GridAutoFitLayoutManager


/*
* Created By mabrouk on 26/09/19
* Cook Meals
*/

class CountryFragment : BaseDialogFragment(), CountryAdapter.CountryListener {
    val countries : ArrayList<CountryModel> = arrayListOf(
        CountryModel("British",R.drawable.fg1),
        CountryModel("American",R.drawable.fg2),
        CountryModel("French",R.drawable.fg3),
        CountryModel("Canadian",R.drawable.fg4),
        CountryModel("Jamaican",R.drawable.fg5),
        CountryModel("Chinese",R.drawable.fg6),
        CountryModel("Dutch",R.drawable.fg7),
        CountryModel("Egyptian",R.drawable.fg8),
        CountryModel("Greek",R.drawable.fg9),
        CountryModel("Indian",R.drawable.fg10),
        CountryModel("Irish",R.drawable.fg11),
        CountryModel("Italian",R.drawable.fg12),
        CountryModel("Japanese",R.drawable.fg13),
        CountryModel("Kenyan",R.drawable.fg14),
        CountryModel("Malaysian",R.drawable.fg15),
        CountryModel("Mexican",R.drawable.fg16),
        CountryModel("Moroccan",R.drawable.fg17),
        CountryModel("Norwegian",R.drawable.fg18),
        CountryModel("Croatian",R.drawable.fg19),
        CountryModel("Portuguese",R.drawable.fg20),
        CountryModel("Russian",R.drawable.fg21),
        CountryModel("Argentinian",R.drawable.fg22),
        CountryModel("Spanish",R.drawable.fg23),
        CountryModel("Slovakian",R.drawable.fg24),
        CountryModel("Thai",R.drawable.fg25),
        CountryModel("Saudi Arabian",R.drawable.fg26),
        CountryModel("Vietnamese",R.drawable.fg27),
        CountryModel("Turkish",R.drawable.fg28),
        CountryModel("Syrian",R.drawable.fg29),
        CountryModel("Algerian",R.drawable.fg30),
        CountryModel("Tunisian",R.drawable.fg31)
    )
    lateinit var layoutBinding: CountriesLayoutBinding
    val adapter:CountryAdapter by lazy {
        CountryAdapter(this)
    }

    override fun initView() {
        layoutBinding=DataBindingUtil.bind(currentView)!!
        layoutBinding.countryRcv.layoutManager=GridAutoFitLayoutManager(context,100)
        layoutBinding.countryRcv.adapter=adapter
         adapter.setData(countries)
    }

    override fun getLayoutResource(): Int = R.layout.countries_layout

    override fun onCountryClick(country: CountryModel) {
       MealsActivity.start(context!!,0,country.name,FiltersType.Area,FoodType.Meals)
        dismiss()
    }

    companion object{
        fun getFragment() : CountryFragment =
            CountryFragment()
    }
}