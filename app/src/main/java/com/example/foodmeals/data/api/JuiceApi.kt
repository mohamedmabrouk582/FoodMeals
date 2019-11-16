package com.example.foodmeals.data.api

import com.example.foodmeals.data.models.MealsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface JuiceApi {


    @GET("lookup.php")
    fun juiceDetails(@Query("i") id:Long) : Deferred<MealsResponse>

    @GET("filter.php")
    fun filter(@Query("a") alcoholic:String?=null, @Query("c") category:String?=null
               , @Query("g") glass:String?=null, @Query("i") ingredient:String?=null) : Deferred<MealsResponse>

    @GET("random.php")
    fun getRandomJuice(): Deferred<MealsResponse>

    @GET("search.php")
    fun search(@Query("s") query: String) : Deferred<MealsResponse>

    @GET("lookup.php")
    fun getJuiceDetails(@Query("i") id:Long) : Deferred<MealsResponse>
}