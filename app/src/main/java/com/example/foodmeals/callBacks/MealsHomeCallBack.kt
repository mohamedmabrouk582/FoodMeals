package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Category
import com.example.foodmeals.data.models.Ingredient
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.ui.fragments.MealsHomeFragment


/*
* Created By mabrouk on 11/09/19
* Cook Meals
*/

interface MealsHomeCallBack : BaseCallBack {
  fun addRandomMel(meal:Meal)
  fun loadCategories(data:ArrayList<Category>)
  fun loadIngredients(data: ArrayList<Ingredient>)
  fun getFragment() : MealsHomeFragment
}