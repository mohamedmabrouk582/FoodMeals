package com.example.foodmeals.viewModels.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodmeals.callBacks.JuiceHomeCallBack
import com.example.foodmeals.callBacks.MealDetailsCallBack
import com.example.foodmeals.callBacks.MealsCallBack
import com.example.foodmeals.callBacks.MealsHomeCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.db.MealDao
import com.example.foodmeals.viewModels.DrinkHomeViewModel
import com.example.foodmeals.viewModels.MealDetailsViewModel
import com.example.foodmeals.viewModels.MealsHomeViewModel
import com.example.foodmeals.viewModels.MealsViewModel


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class BaseViewModelFactory(
   val mealsDao: MealDao
    , val api: BaseApi,val juiceApi: JuiceApi, val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsHomeViewModel::class.java))
            return MealsHomeViewModel<MealsHomeCallBack>(application,api,mealsDao) as T
        else if (modelClass.isAssignableFrom(MealsViewModel::class.java))
            return MealsViewModel<MealsCallBack>(application,api,juiceApi,mealsDao) as T
        else if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java))
            return MealDetailsViewModel<MealDetailsCallBack>(application,api,juiceApi,mealsDao) as T
        else if (modelClass.isAssignableFrom(DrinkHomeViewModel::class.java))
            return DrinkHomeViewModel<JuiceHomeCallBack>(application,juiceApi,mealsDao) as T
        throw IllegalArgumentException("Your View Model Not Found")
    }
}
