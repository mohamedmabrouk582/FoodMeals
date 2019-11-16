package com.example.foodmeals.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodmeals.data.models.Meal


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/
@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class MealDb : RoomDatabase() {
    public abstract fun getMealDao(): MealDao
}
