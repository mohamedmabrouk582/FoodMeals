package com.example.foodmeals.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


class MealsByIngredient {

    @Embedded
    lateinit var ingredient:Ingredient

    @Relation(parentColumn = "idIngredient",entityColumn = "idMeal",associateBy = Junction(MealsWithIngredients::class))
    var meals:List<Meal> = emptyList()
}