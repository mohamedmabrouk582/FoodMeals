package com.example.foodmeals.data.models

import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

data class Ingredient(val idIngredient:Long=0, @SerializedName("strIngredient")val content:String, val desc:String){
    var url:String=""
    get() { return "https://www.themealdb.com/images/ingredients/$content.png" }
}