package com.example.foodmeals.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodmeals.data.models.Category
import com.example.foodmeals.data.models.Ingredient
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.data.models.MealsWithIngredients


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/
@Database(entities = [Meal::class,Category::class,Ingredient::class,MealsWithIngredients::class], version = 2, exportSchema = false)
@TypeConverters(DataConvert::class)
abstract class MealDb : RoomDatabase() {
     abstract fun getMealDao(): MealDao
}
