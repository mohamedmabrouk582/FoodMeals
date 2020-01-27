package com.example.foodmeals.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


class MealsByIngredient {

    @Embedded
    lateinit var ingredient:Ingredient

    @Relation(parentColumn = "strIngredient",entityColumn = "idMeal",associateBy =
    Junction(value = MealsWithIngredients::class , parentColumn = "strIngredient",entityColumn = "idMeal"))
    var meals:List<Meal> = emptyList()
}