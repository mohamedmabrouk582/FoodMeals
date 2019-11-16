package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.ui.activities.MealDetailsActivity


/*
* Created By mabrouk on 13/09/19
* Cook Meals
*/

interface MealDetailsCallBack : BaseCallBack {
    fun loadMeal(meal:Meal)
    fun getMealActivity():MealDetailsActivity
}