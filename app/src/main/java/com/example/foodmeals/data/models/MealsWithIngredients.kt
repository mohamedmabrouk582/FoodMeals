package com.example.foodmeals.data.models

import androidx.room.Entity

@Entity(primaryKeys = ["strIngredient","idMeal"])
data class MealsWithIngredients(val strIngredient:Long,val idMeal:Long)