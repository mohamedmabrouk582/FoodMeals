package com.example.foodmeals.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

@Entity
data class Ingredient(@PrimaryKey @ColumnInfo(name = "idIngredient") val idIngredient:Long=0,  @SerializedName("strIngredient")val content:String, val desc:String?){
    var url:String=""
    get() { return "https://www.themealdb.com/images/ingredients/$content.png" }
}