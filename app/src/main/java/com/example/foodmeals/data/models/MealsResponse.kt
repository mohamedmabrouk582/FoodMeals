package com.example.foodmeals.data.models

import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

data class MealsResponse(@SerializedName(value = "meals",alternate = ["drinks"])val meals:ArrayList<Meal>?)