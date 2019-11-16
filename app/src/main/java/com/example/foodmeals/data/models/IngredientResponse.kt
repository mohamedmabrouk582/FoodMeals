package com.example.foodmeals.data.models

import com.google.gson.annotations.SerializedName

data class IngredientResponse(@SerializedName(value = "meals",alternate = ["drinks"])val meals:ArrayList<Ingredient>?)