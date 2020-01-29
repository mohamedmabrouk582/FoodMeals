package com.example.foodmeals.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodmeals.data.models.*


/*
* Created By mabrouk on 20/03/19
* KotilnApp
 *
*/
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory( category: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal( meal: List<Meal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneMeal( meal: Meal) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(data:List<Ingredient>)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertMealWithIngredient(join:MealsWithIngredients)



    @Query("select * from Category ")
    fun getCategory() : LiveData<List<Category>>


    @Query("select * from ingredient")
    fun getIngredient() : LiveData<List<Ingredient>>


    @Transaction
    @Query("select * from meal where meal_category=:category ")
    fun getMealsByCategory(category: String) : LiveData<List<Meal>>


    @Transaction
    @Query("select * from ingredient ")
    fun getMealsByIngredients() : LiveData<List<MealsByIngredient>>

    @Query("select * from meal where type = :type or mealType=:type")
    fun getMeal(type:FoodType) : LiveData<List<Meal>>

    @Query("select * from meal where type = :type and strMeal like  '%' || :query  || '%' ")
     fun getSearchMeal(type:FoodType,query:String) : LiveData<List<Meal>>

    @Query("select * from meal where type=:type and area=:country")
    fun getMealsByArea(type:FoodType,country: String) : LiveData<List<Meal>>


    @Query("select * from meal where idMeal = :id ")
    fun getMealById(id:Long) : LiveData<Meal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMeal(meal:Meal) : Int

}
