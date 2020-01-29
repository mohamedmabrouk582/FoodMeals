package com.example.foodmeals.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealsWithIngredients(@PrimaryKey(autoGenerate = true)val id:Long,val idIngredient:Long, val idMeal:Long)