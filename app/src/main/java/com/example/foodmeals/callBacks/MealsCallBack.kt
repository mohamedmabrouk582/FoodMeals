package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.ui.activities.MealsActivity


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

interface MealsCallBack : BaseCallBack {
    fun getMealsActivity():MealsActivity
    fun loadMeals(data:ArrayList<Meal>)
}