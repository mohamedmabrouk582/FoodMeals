package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Meal


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

interface MealsCallBack : BaseCallBack {
    fun loadMeals(data:ArrayList<Meal>)
}