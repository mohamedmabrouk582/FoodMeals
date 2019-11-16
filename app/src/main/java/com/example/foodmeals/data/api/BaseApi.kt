package com.example.foodmeals.data.api

import com.example.foodmeals.data.models.*
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseApi {

    @GET("categories.php")
    fun getCategories() : Deferred<CategoryResponse>

    @GET("latest.php")
    fun getLatestMeals() : Deferred<MealsResponse>

    @GET("random.php")
    fun getRandomMeals() : Deferred<MealsResponse>

    @GET("list.php?i=list")
    fun getIngredients() : Deferred<IngredientResponse>

    @GET("filter.php")
    fun getFilterByCatgory(@Query("c") category: String) : Deferred<MealsResponse>

    @GET("filter.php")
    fun getFilterByIngredient(@Query("i") ingredient: String) : Deferred<MealsResponse>

    @GET("filter.php")
    fun getFilterByArea(@Query("a") area: String) : Deferred<MealsResponse>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:Long) : Deferred<MealsResponse>

    @GET("search.php")
    fun searchMeals(@Query("s") query:String) : Deferred<MealsResponse>



    @GET("lookup.php")
    fun juiceDetails(@Query("i") id:Long) : Deferred<MealsResponse>

    @GET("filter.php")
    fun filter(@Query("a") alcoholic:String?=null, @Query("c") category:String?=null
               ,@Query("g") glass:String?=null,@Query("i") ingredient:String?=null) : Deferred<MealsResponse>

    @GET("random.php")
    fun getRandomJuice(): Deferred<MealsResponse>

    @GET("search.php")
    fun search(@Query("s") query: String)


}
